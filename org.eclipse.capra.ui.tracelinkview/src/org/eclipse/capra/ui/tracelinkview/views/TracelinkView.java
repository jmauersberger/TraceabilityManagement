package org.eclipse.capra.ui.tracelinkview.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.operations.CreateConnection;
import org.eclipse.capra.core.util.ArtifactWrappingUtil;
import org.eclipse.capra.core.util.CapraExceptionUtil;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.core.util.TraceLinkAttribute;
import org.eclipse.capra.ui.tracelinkview.Activator;
import org.eclipse.capra.ui.tracelinkview.dialogs.TraceLinkAttributesDialog;
import org.eclipse.capra.ui.tracelinkview.views.CapraTableViewer.IChangeListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 * 
 * @author Sascha Baumgart
 */
public class TracelinkView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.capra.ui.linkview.views.TracelinkView";
	private CapraTableViewer leftViewer;
	private CapraTableViewer rightViewer;
	private Combo linkTypeCombo;
	private Action createLinkAction;
	private Action clearViewersAction;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, true));

		// Label label = new Label(parent, SWT.NONE);
		// label.setText("Link Type: ");
		// label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		linkTypeCombo = new Combo(parent, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		GridData linkTypeGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		linkTypeGridData.horizontalSpan = 2;
		linkTypeCombo.setLayoutData(linkTypeGridData);
		
		leftViewer = new CapraTableViewer(parent, SWT.MULTI | SWT.BORDER);
		leftViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		leftViewer.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange() {
				try {
					refreshAvailableLinkTypes();
				} catch (CapraException e) {
					CapraExceptionUtil.handleException(e, "Exception while refreshing link types");
				}
			}
		});

		rightViewer = new CapraTableViewer(parent, SWT.MULTI | SWT.BORDER);
		rightViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rightViewer.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange() {
				try {
					refreshAvailableLinkTypes();
				} catch (CapraException e) {
					CapraExceptionUtil.handleException(e, "Exception while refreshing link types");
				}
			}
		});

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(leftViewer.getControl(),
				"org.eclipse.capra.ui.tracelinkview.viewer");
		// getSite().setSelectionProvider(leftViewer);
		getSite().setSelectionProvider(new SelectionProvider(rightViewer, leftViewer));
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}

	private void refreshAvailableLinkTypes() throws CapraException {
		int oldSelectionIndex = linkTypeCombo.getSelectionIndex();
		TraceMetaModelAdapter tradeModelAdapter = ExtensionPointUtil.getTraceMetamodelAdapter();

		List<EObject> leftWrappedEObjs;
		List<EObject> rightWrappedEObjs;
		try {
			ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
			leftWrappedEObjs = ArtifactWrappingUtil.wrap(leftViewer.getObjects(), resourceSetImpl);
			rightWrappedEObjs = ArtifactWrappingUtil.wrap(rightViewer.getObjects(), resourceSetImpl);
			List<TraceLinkAdapter> traceTypes = tradeModelAdapter.getAvailableTraceTypes(leftWrappedEObjs,
					rightWrappedEObjs);
			linkTypeCombo.removeAll();

			List<String> linkTypes = new ArrayList<>();
			for (TraceLinkAdapter traceType : traceTypes) {
				String linkType = traceType.getLinkType();
				linkTypes.add(linkType);
			}
			Collections.sort(linkTypes, String.CASE_INSENSITIVE_ORDER);
			for (String linkType : linkTypes) {
				linkTypeCombo.add(linkType);
			}

		} catch (CapraException ex) {
			CapraExceptionUtil.handleException(ex, "Error while Retrieving Trace Link Types");
		}

		// TODO: Some other item with the same index in the combo could be
		// selected.
		int newItemCount = linkTypeCombo.getItemCount();
		if (oldSelectionIndex == -1 && newItemCount > 0) {
			linkTypeCombo.select(0);
		} else if (oldSelectionIndex >= 0 && linkTypeCombo.getItemCount() > oldSelectionIndex) {
			linkTypeCombo.select(oldSelectionIndex);
		}

		linkTypeCombo.update();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TracelinkView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(leftViewer.getControl());
		leftViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, leftViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(createLinkAction);
		manager.add(clearViewersAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(createLinkAction);
		manager.add(clearViewersAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(createLinkAction);
		manager.add(clearViewersAction);
	}

	private void makeActions() {
		createLinkAction = new Action() {
			public void run() {
				String traceType = linkTypeCombo.getText();
				List<Object> sources = leftViewer.getObjects();
				List<Object> targets = rightViewer.getObjects();
			
				try {
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					//attributes
					TraceLinkAdapter traceLinkAdapter = ExtensionPointUtil.getTraceLinkAdapter(traceType);
//					System.out.println("*traceLinkAdapter: " + traceLinkAdapter.getLinkType());
					List<TraceLinkAttribute> attributes = traceLinkAdapter.getAttributes();
					if(attributes != null && attributes.size() > 0){
//						System.out.println("*with " + attributes.size() + " attributes");
//						for(TraceLinkAttribute attr : attributes){
//							System.out.println("*Name: " + attr.getName() + " type: " + attr.getType() + " value: " + attr.getValue());
//						}
						//pass attributes to a Dialog Window with a table/form to view and edit
						
						TraceLinkAttributesDialog dialog = new TraceLinkAttributesDialog(shell, attributes);
						if (dialog.open() == Window.OK) {
							attributes = dialog.getAttributes();
//							for(TraceLinkAttribute attr : attributes){
//								System.out.println("***Name: " + attr.getName() + " type: " + attr.getType() + " value: " + attr.getValue());
//							}
						}
					}
					CreateConnection createConnection = new CreateConnection(sources, targets, traceType, attributes);
					createConnection.execute();
					MessageDialog.openInformation(shell, "Trace", "Trace has been created.");

					
				} catch (CapraException ex) {
					CapraExceptionUtil.handleException(ex, "Create Connection Failed");
				}

			}
		};
		createLinkAction.setText("Create Trace Link");
		createLinkAction.setToolTipText("Create Trace Link");
		createLinkAction.setImageDescriptor(Activator.getImageDescriptor("icons/famfamfam/link_add.png"));

		// Enablement
		linkTypeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateActionEnablement();
			}

		});
		leftViewer.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange() {
				updateActionEnablement();
			}
		});
		rightViewer.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange() {
				updateActionEnablement();
			}
		});

		// Define clearViewersAction
		clearViewersAction = new Action() {
			public void run() {
				leftViewer.clearSelection();
				rightViewer.clearSelection();
			}
		};
		clearViewersAction.setText("Clear View");
		clearViewersAction.setToolTipText("Clear View");
		clearViewersAction.setImageDescriptor(Activator.getImageDescriptor("icons/famfamfam/cross.png"));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// leftViewer.getControl().setFocus();
	}

	private void updateActionEnablement() {
		boolean enableLinkCreation = (linkTypeCombo.getText() != null && linkTypeCombo.getText().length() > 0
				&& !leftViewer.getObjects().isEmpty() && !rightViewer.getObjects().isEmpty());
		createLinkAction.setEnabled(enableLinkCreation);

		boolean enableClearViewers = (linkTypeCombo.getText() != null && linkTypeCombo.getText().length() > 0)
				|| !leftViewer.getObjects().isEmpty() || !rightViewer.getObjects().isEmpty();
		clearViewersAction.setEnabled(enableClearViewers);
	}

	public static TracelinkView getOpenedView() {
		try {
			return (TracelinkView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void addToSources(List<Object> selection) {
		try {
			leftViewer.addToSelection(selection);
		} catch (CapraException e) {
			CapraExceptionUtil.handleException(e, "Error while Dropping");
		}
	}

	public void addToTargets(List<Object> selection) {
		try {
			rightViewer.addToSelection(selection);
		} catch (CapraException e) {
			CapraExceptionUtil.handleException(e, "Error while Dropping");
		}
	}
}
