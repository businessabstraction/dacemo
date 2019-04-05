package DAO;

import Bean.Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphDAOImpl implements GraphDAO {

    @Override
    /**
     * The DAO creation function to build the graph
     */
    public List<Graph> findAll() {
        List<Graph> graphs = new ArrayList<>();
        graphs.add(new Graph("A",1));
        graphs.add(new Graph("B",2));
        graphs.add(new Graph("C",3));
        return graphs;
    }
}
