package functions;



public interface MathFunction {
    double EPSILON=1e-9;
    double apply(double x);
    default String Name(){
        return this.getClass().getSimpleName();
    }
    default int HashName() {
        return this.Name().hashCode();
    }
    default CompositeFunction andThen(MathFunction afterFunction){
        return new CompositeFunction(afterFunction, this);
    }
}
