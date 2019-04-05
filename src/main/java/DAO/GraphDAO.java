package DAO;

import Bean.Graph;

import java.util.List;

/**
 * The interface DAO of graph
 */
public interface GraphDAO {
    List<Graph> findAll();
}
