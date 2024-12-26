package persistence;

import functions.ConstantFunction;
import functions.MathFunction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.dao.MathFunctionEntityDAO;
import persistence.dao.UserDAO;
import persistence.entity.MathFunctionEntity;
import persistence.entity.PointEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersistenceTest {
    private static  MathFunctionEntity testMathFunctionEntity;
    private static UserDAO userDAO;
    private  static MathFunctionEntityDAO mathFunctionEntityDAO;
    @BeforeAll
    static void  settingUp(){
        mathFunctionEntityDAO = new MathFunctionEntityDAO();
        userDAO=new UserDAO();
    }
    @BeforeEach
    void stillSettingUp(){
        List<PointEntity> pointlist= new ArrayList<>();
        PointEntity ent=new PointEntity();
        PointEntity ent1=new PointEntity();
        ent.setX(Double.valueOf(0));
        ent.setY(Double.valueOf(2));
        ent1.setY(Double.valueOf(0));
        ent1.setX(Double.valueOf(1));
        testMathFunctionEntity=new MathFunctionEntity();
        testMathFunctionEntity.setName("sample_name");
        MathFunction testFunction=new ConstantFunction(2);
        testMathFunctionEntity.setFunction_id(Long.valueOf(testFunction.HashName()));
        ent.setX(Double.valueOf(0));
        ent.setY(Double.valueOf(testFunction.apply(0)));
        ent1.setY(Double.valueOf(testFunction.apply(1)));
        ent1.setX(Double.valueOf(1));
        ent.setMathFunctionEntity(testMathFunctionEntity);
        ent1.setMathFunctionEntity(testMathFunctionEntity);
        pointlist.add(ent1);
        pointlist.add(ent);
        testMathFunctionEntity.setPoints(pointlist);
    }
    @Test
    void createRead_ExpectReadEqualCreate(){
        mathFunctionEntityDAO.create(testMathFunctionEntity);
        Optional<MathFunctionEntity> optionalMathFunctionEntity=mathFunctionEntityDAO.read(testMathFunctionEntity.getFunction_id());
        assertEquals(optionalMathFunctionEntity.orElse(null).getPoints().getFirst().getY(),testMathFunctionEntity.getPoints().getFirst().getY());
    }
    @Test
    void updateRead_ExpectReadEqualUpdate(){
        PointEntity ent2=new PointEntity(Double.valueOf(2),Double.valueOf(3),testMathFunctionEntity);
        testMathFunctionEntity.addPoint(ent2);
        mathFunctionEntityDAO.update(testMathFunctionEntity);
        testMathFunctionEntity.removeLastPoint();
        mathFunctionEntityDAO.update(testMathFunctionEntity);
        List<MathFunctionEntity> listByNameMathFunctionEntity=mathFunctionEntityDAO.readByName("sample_name");
        List<MathFunctionEntity> listAllMathFunctionEntity=mathFunctionEntityDAO.readAll();
        assertEquals(Double.valueOf(1),listByNameMathFunctionEntity.getFirst().getPoints().getLast().getX());
        assertEquals(Double.valueOf(1),listAllMathFunctionEntity.getLast().getPoints().getLast().getX());
    }
    @AfterAll
    static void wrappingUp(){
        mathFunctionEntityDAO.delete(testMathFunctionEntity.getFunction_id());
        mathFunctionEntityDAO.detachAll();
        userDAO.detachAll();
    }
}