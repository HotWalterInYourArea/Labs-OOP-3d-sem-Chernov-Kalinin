package api.controller;

import api.dto.MathFunctionDTO;
import api.dto.PointDTO;
import api.service.MathFunctionService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MathFunctionResourceTest {
    private static final MathFunctionResource mathResource=new MathFunctionResource();
    private static final MathFunctionService mathService=new MathFunctionService();
    private final static MathFunctionDTO mathDTO=new MathFunctionDTO();
    @BeforeAll
    static void  settingUp(){
        mathDTO.setName("sample_name");
        mathDTO.setId(Long.valueOf(5));
    }
    @Test
    void postAndPutPointMathTest_ExpectServiceReadEqual_ValidDTO(){
        mathResource.postMathFunction(mathDTO);
        assertEquals(mathDTO,mathService.readById(mathDTO.getId()));
        PointDTO point =new PointDTO();
        point.setX(Double.valueOf(1));
        point.setY(Double.valueOf(2));
        point.setMathId(mathDTO.getId());
        List<PointDTO> points=new ArrayList<>();
        points.add(point);
        mathDTO.setListOfPoints(points);
        mathResource.putNewPoint(mathDTO.getId(),point);
        assertEquals(mathDTO.getListOfPoints().getFirst().getX(),mathService.readById(mathDTO.getId()).getListOfPoints().getFirst().getX());
        assertEquals(points,mathResource.getMathFunctionPoints(mathDTO.getId()).getEntity());
    }
    @Test
    void getUserTest_ExpectServiceReadEqual_ValidUserName(){
        assertEquals(200,mathResource.getMathFunction(mathDTO.getId()).getStatus());
    }
    @Test
    void getUserTest_ExpectServiceReadEqual_InvalidUserName(){
        assertEquals(400,mathResource.getMathFunction(Long.valueOf(2)).getStatus());
    }
    @Test
    void deleteUserTest_ExpectServiceReadEqual_ValidUserId(){
        MathFunctionDTO nu_func=new MathFunctionDTO();
        nu_func.setName("123");
        nu_func.setId(Long.valueOf(2));
        mathResource.postMathFunction(nu_func);
        mathResource.deleteFunction(nu_func.getId());
        assertNull(mathService.readById(nu_func.getId()));
    }
    @Test
    void deleteUserTest_ExpectServiceReadEqual_InvalidUserId(){
        MathFunctionDTO nu_func=new MathFunctionDTO();
        nu_func.setName("123");
        nu_func.setId(Long.valueOf(2));
        mathResource.postMathFunction(nu_func);
        assertEquals(400,mathResource.deleteFunction(Long.valueOf(3)).getStatus());
        mathResource.deleteFunction(nu_func.getId());
    }
    @AfterAll
    static void closing(){
        mathService.delete(mathDTO.getId());
    }


}