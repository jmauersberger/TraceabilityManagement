package org.eclipse.capra.ui.tracelinkview.views;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.operations.CreateConnection;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class CreateTraceOperation extends AbstractOperation {
	
	private CreateConnection createConnection;
	
	public CreateTraceOperation(CreateConnection createConnection) {
		super("Create Trace");
		this.createConnection = createConnection;;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			createConnection.execute();
		} catch (CapraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			createConnection.redo();
		} catch (CapraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			createConnection.undo();
		} catch (CapraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	
	}

}
