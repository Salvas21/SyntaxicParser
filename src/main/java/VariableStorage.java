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

    public void update(String key, String value) {
        Variable variable = null;
        for (int i = 0; i < variables.size() && variable == null; i++) {
            if (variables.get(i).getKey().equals(key)) {
                variable = variables.get(i);
                variable.setValue(value);
                variables.set(i, variable);
                // TODO : make sure value is of good type
            }
        }
    }
}
