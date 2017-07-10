package org.eclipse.capra.ui.linkview.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

/**
 * Sets up the ContentProvider, LabelProvider and DND.
 * 
 * @author baumgart
 */
public class CapraTableViewer extends TableViewer {
	public interface IChangeListener {
		void handleChange();
	}

	/** The maintained selection of EObjects */
	private List<ObjectWithHandler> selection = new ArrayList<ObjectWithHandler>();
	private List<IChangeListener> listeners = new ArrayList<>();

	public CapraTableViewer(Composite parent, int style) {
		super(parent, style);

		setContentProvider(new ArrayContentProvider());
		setLabelProvider(new CapraTableLabelProvider());
		setInput(selection);

		// DND.DROP_MOVE deletes files when dropping files outside of eclipse.
		// Press CTRL for link.
		int ops = DND.DROP_COPY | DND.DROP_LINK | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] { org.eclipse.ui.part.ResourceTransfer.getInstance(),
				org.eclipse.ui.part.EditorInputTransfer.getInstance(), org.eclipse.swt.dnd.FileTransfer.getInstance(),
				org.eclipse.swt.dnd.RTFTransfer.getInstance(), org.eclipse.swt.dnd.TextTransfer.getInstance(),
				org.eclipse.swt.dnd.URLTransfer.getInstance(),
				org.eclipse.jface.util.LocalSelectionTransfer.getTransfer(),
				org.eclipse.emf.edit.ui.dnd.LocalTransfer.getInstance() };

		addDropSupport(ops, transfers, new CapraSelectionDropAdapter(this));
	}

	public List<Object> getObjects() {
		return selection.stream().map(objectWithHandler -> objectWithHandler.o).collect(Collectors.toList());
	}

	public void clearSelection() {
		selection.clear();
		refresh();
		listeners.stream().forEach(listener -> listener.handleChange());
	}

	public void removeFromSelection(List<Object> currentselection) {
		selection.removeAll(currentselection);
		refresh();
		listeners.stream().forEach(listener -> listener.handleChange());
	}

	public void addToSelection(List<Object> list) {
		list.stream().forEach(obj -> addToSelection(obj));
		listeners.stream().forEach(listener -> listener.handleChange());
	}

	public void addToSelection(Object data) {
		Collection<?> coll = null;
		if (data instanceof TreeSelection) {
			TreeSelection tree = (TreeSelection) data;
			coll = tree.toList();
		} else if (data instanceof Collection<?>) {
			coll = (Collection<?>) data;
		} else {
			coll = Collections.singleton(data);
		}

		for (Object o : coll) {
			ArtifactHandler handler = getHandler(o);
			if (handler != null) {
				ObjectWithHandler objectWithLabel = new ObjectWithHandler(o, handler);
				selection.add(objectWithLabel);
			}
		}

		refresh();
		listeners.stream().forEach(listener -> listener.handleChange());
	}

	private ArtifactHandler getHandler(Object target) {
		Collection<ArtifactHandler> artifactHandlers = ExtensionPointUtil.getArtifactHandlers();
		List<ArtifactHandler> availableHandlers = artifactHandlers.stream()
				.filter(handler -> handler.canHandleSelection(target)).collect(Collectors.toList());
		Optional<PriorityHandler> priorityHandler = ExtensionPointUtil.getPriorityHandler();
		if (availableHandlers.size() == 0) {
			MessageDialog.openWarning(getControl().getShell(), "No handler for selected item",
					"There is no handler for " + target + " so it will be ignored.");
			return null;
		} else if (availableHandlers.size() > 1 && !priorityHandler.isPresent()) {
			MessageDialog.openWarning(getControl().getShell(), "Multiple handlers for selected item",
					"There are multiple handlers for " + target + " so it will be ignored.");
			return null;
		} else if (availableHandlers.size() > 1 && !priorityHandler.isPresent()) {
			return priorityHandler.get().getSelectedHandler(availableHandlers, target);
		} else
			return availableHandlers.get(0);
	}

	public void addChangeListener(IChangeListener listener) {
		listeners.add(listener);
	}

	public void removeChangeListener(IChangeListener listener) {
		listeners.remove(listener);
	}
}
