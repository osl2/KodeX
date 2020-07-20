package kodex.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This class contains the list of selected procedure to be displayed on the
 * start page. It can perform operations such as sorting and searching on the
 * list. Every time a new instance of this class is created, it gets the current
 * list of all activated procedures from the Singleton instance of the
 * PluginLoader and is therefore always up to date.
 * 
 * @author Patrick Spiesberger
 * @author Leonhard Kraft
 *
 * @version 1.0
 */
public class IndexPage {

    /* List of all aviable procedures */
    private ObservableList<ProcedurePlugin> availableProcedures = FXCollections.observableArrayList();

    /* List of all procedures according to the desired restriction */
    private ObservableList<ProcedurePlugin> selectedProcedures = FXCollections.observableArrayList();

    /**
     * Constructor of class IndexPage. Loads the current list of all activated
     * procededures from PluginLoader.
     */
    public IndexPage() {
        availableProcedures = PluginLoader.getInstance().getEnabledProcedurePlugins();

        // all procedures are selected at the start
        selectedProcedures = FXCollections.observableArrayList(availableProcedures);
    }

    /**
     * @param procedures
     */
    public void updateProcedures(List<ProcedurePlugin> procedures) {
    }

    /**
     * Returns a list of those procedures related to the one entered Keyword match.
     * 
     * @param searchTerm : search query
     * @return list of procedure plugins
     */
    public void findProcedures(String searchTerm) {

        ProcedureInformation info;
        String nameLower;
        String searchLower = searchTerm.toLowerCase();

        for (ProcedurePlugin plugin : availableProcedures) {

            info = plugin.createProcedureInformation();
            nameLower = info.getName().toLowerCase();

            if (nameLower.contains(searchLower)
                    || info.getLabels().stream().anyMatch(s -> s.equalsIgnoreCase(searchTerm))) {
                // plugin is a valid search result

                if (!selectedProcedures.contains(plugin)) {
                    // list should be unique
                    selectedProcedures.add(plugin);
                }
            } else {
                selectedProcedures.remove(plugin);
            }
        }
    }

    /**
     * Returns a list of those procedures related to the selected one Filter methods
     * fit. It uses a strategy that uses exactly the filtering method that matches
     * the selected filter.
     * 
     * @param filter : concrete strategy
     * @return list of procedure plugins
     */
    public void filterProcedures(Filter filter) {

        FilterStrategy strategy = null;

        if (filter.equals(Filter.ALPHABETIC)) {
            strategy = new AlphaNumericalSort();
            
        } else if (filter.equals(Filter.GRADE)) {
            strategy = new LabelSort();
            
        } else if (filter.equals(Filter.RELEVANCE)) {
            strategy = new RelevancySort();
            
        } else if (filter.equals(Filter.NO_FILTER)) {
            resetSelectedProcedures();
            return;
            
        } else {
            System.out.println("no suitable filter found");
            resetSelectedProcedures();
            return;
        }

        strategy.filterProcedures(selectedProcedures);
    }

    private void resetSelectedProcedures() {
        // clear and add to avoid creation of a new list instance which would destroy
        // all references
        selectedProcedures.clear();
        selectedProcedures.addAll(availableProcedures);
    }

    /**
     * Increases the entered value for relevance in Sorting_relevancy.properties for
     * the given plugin.
     * 
     * @param plugin : The procedure whose relevance should be increased
     */
    public void increaseRelevancy(ProcedurePlugin plugin) {
        String url = "sorting_relevancy.properties";
        InputStream input = getClass().getResourceAsStream(url);
        Properties prop = new Properties();
        ;

        try {
            prop.load(input);

            // plugin already stored in property file
            if (prop.getProperty(plugin.createProcedureInformation().getName()) != null) {
                int value = Integer.valueOf(prop.getProperty(plugin.createProcedureInformation().getName()));
                prop.setProperty(plugin.createProcedureInformation().getName(), String.valueOf(value + 1));
            }
            // plugin not stored in property file --> first call
            else {
                prop.setProperty(plugin.createProcedureInformation().getName(), String.valueOf(1));
            }

            storeProperties(prop);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Stores changed properties
     */
    private void storeProperties(Properties prop) {
        try {
            prop.store(new FileOutputStream(getClass().getResource("").getPath() + "/sorting_relevancy.properties"),
                    null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ProcedurePlugin> getSelectedProcedures() {
        return FXCollections.unmodifiableObservableList(selectedProcedures);
    }

}