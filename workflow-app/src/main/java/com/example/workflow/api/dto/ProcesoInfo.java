package com.example.workflow.api.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcesoInfo {
    private String id;
    private String businessKey;
    private List<String> actividades;
    private Map<String, Object> variables;

    public ProcesoInfo(String id, String businessKey, List<String> actividades, Map<String, Object> variables) {
        this.id = id;
        this.businessKey = businessKey;
        this.actividades = actividades;
        // Limpiamos las variables al recibirlas
        this.variables = limpiarVariables(variables);
    }

    /**
     * Este método detecta si una variable es un String con JSON escapado
     * (típico de Tasklist) y le quita las barras invertidas.
     */
    private Map<String, Object> limpiarVariables(Map<String, Object> vars) {
        if (vars == null) return null;

        return vars.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    Object value = entry.getValue();
                    // Si es un String y parece un JSON escapado (ej: "[\"valor\"]")
                    if (value instanceof String) {
                        String strValue = (String) value;
                        if (strValue.contains("\\\"")) {
                            // Quitamos las barras para que Jackson lo trate como texto limpio
                            return strValue.replace("\\\"", "\"");
                        }
                    }
                    return value;
                }
            ));
    }

    public String getId() {
        return id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public List<String> getActividades() {
        return actividades;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }
}