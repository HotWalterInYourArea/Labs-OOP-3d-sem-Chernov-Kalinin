package api.service;

import api.dto.MathFunctionDTO;
import api.dto.PointDTO;
import lombok.NoArgsConstructor;
import persistence.dao.MathFunctionEntityDAO;
import persistence.entity.MathFunctionEntity;
import persistence.entity.PointEntity;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
public class MathFunctionService {
    private final static MathFunctionEntityDAO mathFunctionEntityDAO=new MathFunctionEntityDAO();
    public MathFunctionDTO create (MathFunctionDTO mathFunctionDTO){
        mathFunctionEntityDAO.create(toEntity(mathFunctionDTO));
        MathFunctionEntity mathFunctionEntity=mathFunctionEntityDAO.read(mathFunctionDTO.getId()).orElse(null);
        return toDto(mathFunctionEntity);
    }
    public MathFunctionDTO readById(Long ID){
        return toDto(mathFunctionEntityDAO.read(ID).orElse(null));
    }
    public MathFunctionDTO update(MathFunctionDTO mathFunctionDTO){
        mathFunctionEntityDAO.update(toEntity(mathFunctionDTO));
        MathFunctionEntity mathFunctionEntity=mathFunctionEntityDAO.read(mathFunctionDTO.getId()).orElse(null);
        return toDto(mathFunctionEntity);
    }
    public void delete(Long Id){
        mathFunctionEntityDAO.delete(Id);
    }
    public List<PointDTO> readPointList(Long ID){
        return toDto(mathFunctionEntityDAO.read(ID).orElse(null)).getListOfPoints();
    }
    private static PointDTO toDto(PointEntity pointEntity) {
        if(pointEntity==null) return null;
        return new PointDTO(pointEntity.getX(),pointEntity.getY(),
                pointEntity.getMathFunctionEntity().getFunction_id());
    }
    private static MathFunctionDTO toDto(MathFunctionEntity mathFunctionEntity) {
        if(mathFunctionEntity==null) return null;
        List<PointDTO> pointDTOS=new ArrayList<>();
        for(PointEntity p :mathFunctionEntity.getPoints()){
            pointDTOS.add(toDto(p));
        }
        return new MathFunctionDTO(mathFunctionEntity.getFunction_id(), mathFunctionEntity.getName(),
                mathFunctionEntity.getCreated_at(),
                mathFunctionEntity.getUpdated_at(),pointDTOS);
    }
    private static PointEntity toEntity(PointDTO dto) {
        PointEntity entity = new PointEntity();
        entity.setMathFunctionEntity(mathFunctionEntityDAO.read(dto.getMathId()).orElse(null));
        entity.setX(dto.getX());
        entity.setY(dto.getY());
        return entity;
    }
    private static MathFunctionEntity toEntity(MathFunctionDTO dto) {
        MathFunctionEntity entity = new MathFunctionEntity();
        entity.setFunction_id(dto.getId());
        entity.setName(dto.getName());
        List<PointEntity> pointEntityList = new ArrayList<>();
        if(dto.getListOfPoints()!=null) {
            for (PointDTO p : dto.getListOfPoints()) {
                pointEntityList.add(toEntity(p));
            }
        }
        entity.setPoints(pointEntityList);
        return entity;
    }
}
