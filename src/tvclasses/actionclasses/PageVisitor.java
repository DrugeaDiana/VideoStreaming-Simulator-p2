package tvclasses.actionclasses;

import inputclasses.ActionInput;
import tvclasses.TV;
import tvclasses.pageclasses.RegisterPage;
import tvclasses.pageclasses.HomepageNon;
import tvclasses.pageclasses.HomepageAuth;
import tvclasses.pageclasses.LoginPage;
import tvclasses.pageclasses.SeeDetailsPage;
import tvclasses.pageclasses.UpgradesPage;
import tvclasses.pageclasses.MoviesPage;

/**
 * Visitor Pattern
 */
public interface PageVisitor {
    /**
     * Applies either the ChangePageVisitor or the OnPageVisitor to the LoginPage class
     * @param loginPage active page
     * @param actionInput action
     * @param tv main variable
     */
    void visit(LoginPage loginPage, ActionInput actionInput, TV tv);

    /**
     * Applies either the ChangePageVisitor or the OnPageVisitor to the RegisterPage class
     * @param registerPage active page
     * @param actionInput action
     * @param tv main variable
     */
    void visit(RegisterPage registerPage, ActionInput actionInput, TV tv);

    /**
     * Applies either the ChangePageVisitor or the OnPageVisitor to the MoviesPage class
     * @param moviesPage active page
     * @param actionInput action
     * @param tv main variable
     */
    void visit(MoviesPage moviesPage, ActionInput actionInput, TV tv);

    /**
     * Applies either the ChangePageVisitor or the OnPageVisitor to the SeeDetailsPage class
     * @param seeDetailsPage active page
     * @param actionInput action
     * @param tv main variable
     */
    void visit(SeeDetailsPage seeDetailsPage, ActionInput actionInput, TV tv);

    /**
     * Applies either the ChangePageVisitor or the OnPageVisitor to the UpgradesPage class
     * @param upgradesPage active page
     * @param actionInput action
     * @param tv main variable
     */
    void visit(UpgradesPage upgradesPage, ActionInput actionInput, TV tv);


    /**
     * Applies either the ChangePageVisitor or the OnPageVisitor to the HomepageAuth class
     * @param homepageAuth active page
     * @param actionInput action
     * @param tv main variable
     */
    void visit(HomepageAuth homepageAuth, ActionInput actionInput, TV tv);

    /**
     * Applies either the ChangePageVisitor or the OnPageVisitor to the HomepageNon class
     * @param homepageNon active page
     * @param actionInput action
     * @param tv main variable
     */
    void visit(HomepageNon homepageNon, ActionInput actionInput, TV tv);
}
