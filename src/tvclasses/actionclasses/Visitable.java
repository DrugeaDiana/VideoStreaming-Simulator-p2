package tvclasses.actionclasses;

import inputclasses.ActionInput;
import tvclasses.TV;

/**
 * Visitor Pattern
 */
public interface Visitable {
    /**
     * Function to accept the visitors and to use the visit method in them for the specific
     * class.
     * @param v the specific visitor (either ChangePage or OnPage)
     * @param actionInput action
     * @param tv main variable
     */
    void accept(PageVisitor v, ActionInput actionInput, TV tv);
}
