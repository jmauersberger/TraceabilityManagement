package org.eclipse.capra.ui.plantuml;

import java.util.Collection;
import java.util.List;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.core.util.UIStringUtil;
import org.eclipse.capra.ui.plantuml.Connections;
import org.eclipse.capra.ui.plantuml.util.Connection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class VisualizationHelper {
  public static String createMatrix(final EObject traceModel, final Collection<EObject> firstElements, final Collection<EObject> secondElements) {
    try {
      String _xblockexpression = null;
      {
        final TraceMetaModelAdapter traceAdapter = ExtensionPointUtil.getTraceMetamodelAdapter();
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("@startuml");
        _builder.newLine();
        _builder.append("salt");
        _builder.newLine();
        _builder.append("{#");
        _builder.newLine();
        {
          if ((firstElements != null)) {
            _builder.append(".");
            {
              for(final EObject e : secondElements) {
                _builder.append("|");
                String _createUIString = UIStringUtil.createUIString(e);
                _builder.append(_createUIString);
              }
            }
            _builder.newLineIfNotEmpty();
            {
              for(final EObject first : firstElements) {
                String _createUIString_1 = UIStringUtil.createUIString(first);
                _builder.append(_createUIString_1);
                _builder.append(" ");
                {
                  for(final EObject second : secondElements) {
                    _builder.append("|");
                    {
                      boolean _isEmpty = traceAdapter.getTracesBetween(first, second, traceModel).isEmpty();
                      if (_isEmpty) {
                        _builder.append("X");
                      } else {
                        _builder.append(".");
                      }
                    }
                  }
                }
                _builder.newLineIfNotEmpty();
              }
            }
          } else {
            _builder.append("Choose two containers to show a traceability matrix of their contents.");
            _builder.newLine();
          }
        }
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
        _builder.append("@enduml");
        _builder.newLine();
        _xblockexpression = _builder.toString();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static String createNeighboursView(final List<Connection> connections, final EObject selectedObject) {
    String _xblockexpression = null;
    {
      Connections helper = new Connections(connections, selectedObject);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@startuml");
      _builder.newLine();
      _builder.append("object \"");
      String _originLabel = helper.originLabel();
      _builder.append(_originLabel);
      _builder.append("\" as ");
      String _originId = helper.originId();
      _builder.append(_originId);
      _builder.append(" #pink");
      _builder.newLineIfNotEmpty();
      {
        Collection<String> _objectIdsWithoutOrigin = helper.objectIdsWithoutOrigin();
        for(final String id : _objectIdsWithoutOrigin) {
          _builder.append("object \"");
          String _label = helper.label(id);
          _builder.append(_label);
          _builder.append("\" as ");
          _builder.append(id);
          _builder.newLineIfNotEmpty();
        }
      }
      {
        List<String> _arrows = helper.arrows();
        for(final String a : _arrows) {
          _builder.append(a);
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("@enduml");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
