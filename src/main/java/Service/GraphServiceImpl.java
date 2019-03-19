package Service;

import Bean.Graph;
import DAO.GraphDAO;
import DAO.GraphDAOImpl;

import java.util.List;

public class GraphServiceImpl implements GraphService{
    GraphDAO graphDao;

    public GraphServiceImpl (){
        graphDao = new GraphDAOImpl();
    }

    @Override
    public List<Graph> getGraph() {
        return graphDao.findAll();
    }
}