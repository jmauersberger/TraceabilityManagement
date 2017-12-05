package org.eclipse.capra.ui.tracelinkview.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public class SelectionProvider implements ISelectionProvider {
	private CapraTableViewer rightViewer;
	private CapraTableViewer leftViewer;

	private List<ISelectionChangedListener> listeners;

	public SelectionProvider(CapraTableViewer rightViewer, CapraTableViewer leftViewer) {
		this.rightViewer = rightViewer;
		this.leftViewer = leftViewer;
		listeners = new ArrayList<>();

		rightViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChanged();
			}
		});
		leftViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChanged();
			}
		});
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public ISelection getSelection() {
		StructuredSelection rightSelection = (StructuredSelection) rightViewer.getSelection();
		StructuredSelection leftSelection = (StructuredSelection) leftViewer.getSelection();

		int rightSize = rightSelection.size();
		int leftSize = leftSelection.size();
		Object[] rightObjects = rightSelection.toArray();
		Object[] leftObjects = leftSelection.toArray();

		Object[] objects = new Object[rightSize + leftSize];

		for (int i = 0; i < rightSize + leftSize; i++) {
			if (i < rightSize) {
				objects[i] = rightObjects[i];
			} else {
				objects[i] = leftObjects[i - rightSize];
			}
		}

		return new StructuredSelection(objects);
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
	}

	private void fireSelectionChanged() {
		SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		fireSelectionChanged(event);
	}
	
	private void fireSelectionChanged(SelectionChangedEvent event) {
		for (ISelectionChangedListener listener : listeners) {
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					listener.selectionChanged(event);
				}
			});
		}
	}
}
