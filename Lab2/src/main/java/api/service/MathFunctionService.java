package api.service;

import io.dropwizard.hibernate.UnitOfWork;
import persistence.dao.MathFunctionEntityDAO;
import persistence.entity.MathFunctionEntity;

public class MathFunctionService {
    private MathFunctionEntityDAO mathFunctionEntityDAO;
    public void create (MathFunctionEntity mathFunctionEntity){
        mathFunctionEntityDAO.create(mathFunctionEntity);
    }
    public void read(MathFunctionEntity mathFunctionEntity){
        mathFunctionEntityDAO.read(mathFunctionEntity);
    }
    public void update(MathFunctionEntity mathFunctionEntity){
        mathFunctionEntityDAO.update(mathFunctionEntity);
    }
    public void delete(MathFunctionEntity mathFunctionEntity){
        mathFunctionEntityDAO.delete(mathFunctionEntity);
    }

}
