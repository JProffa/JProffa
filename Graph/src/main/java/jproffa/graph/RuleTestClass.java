/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jproffa.graph;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RuleTestClass implements TestRule {
    
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println("Nyt alkaa testi " + description.getClassName() + " " + description.getMethodName());
                try {
                    base.evaluate();
                } finally {
                    System.out.println("Nyt loppui testi " + description.getClassName() + " " + description.getMethodName());
                }
            }
        };
    }

}
