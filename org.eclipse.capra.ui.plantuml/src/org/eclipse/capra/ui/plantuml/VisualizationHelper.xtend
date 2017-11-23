package org.eclipse.capra.ui.plantuml

import java.util.Collection
import java.util.List
import org.eclipse.capra.core.util.ExtensionPointUtil
import org.eclipse.capra.ui.plantuml.util.Connection
import org.eclipse.capra.ui.plantuml.util.EMFHelper
import org.eclipse.emf.ecore.EObject

class VisualizationHelper {
	def static String createMatrix(EObject traceModel, Collection<EObject> firstElements,
		Collection<EObject> secondElements) {
		val traceAdapter = ExtensionPointUtil.getTraceMetamodelAdapter()
		'''
			@startuml
			salt
			{#
			«IF firstElements != null»
				.«FOR e : secondElements»|«EMFHelper.getIdentifier(e)»«ENDFOR»
				«FOR first : firstElements»
				«EMFHelper.getIdentifier(first)» «FOR second : secondElements»|«IF traceAdapter.getTracesBetween(first, second, traceModel).isEmpty()»X«ELSE».«ENDIF»«ENDFOR»
			«ENDFOR»
			«ELSE»
				Choose two containers to show a traceability matrix of their contents.
			«ENDIF»
			}
			
			@enduml
		'''
	}

	def static String createNeighboursView(List<Connection> connections, EObject selectedObject) {
		var helper = new Connections(connections, selectedObject);
		'''
			@startuml
			object "«helper.originLabel()»" as «helper.originId()» #pink
			«FOR id : helper.objectIdsWithoutOrigin()»
				object "«helper.label(id)»" as «id»
			«ENDFOR»
			«FOR a : helper.arrows()» 
				«a»
			«ENDFOR» 
			@enduml
		'''
	}
}
