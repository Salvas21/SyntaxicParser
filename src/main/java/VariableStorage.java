import java.util.ArrayList;

public class VariableStorage {
    private ArrayList<Variable> variables;

    public VariableStorage() {
        variables = new ArrayList<>();
    }

    public Variable getVariable(String key) {
        for (Variable var : variables) {
            if (var.getKey().equals(key)) return var;
        }
        return null;
    }

    public void add(String key, String type) {
        variables.add(new Variable(key, type));
    }

    public void remove(String key) {
        variables.removeIf(v -> v.getKey().equals(key));
    }

    public boolean update(String key, String value) {
        Variable variable = null;
        boolean status = false;
        for (int i = 0; i < variables.size() && variable == null; i++) {
            if (variables.get(i).getKey().equals(key)) {
                variable = variables.get(i);
                if (variable.isRightType(value)) {
                    variable.setValue(value);
                    variables.set(i, variable);
                    status = true;
                }
            }
        }
        // variable invalide ou type invalide
        return status;
    }
}
