package functions;

import ui.annotations.SimpleFunction;

import java.io.Serial;

@SimpleFunction(canonName="Единичная функция")
public class UnitFunction extends ConstantFunction{
    @Serial
    private static final long serialVersionUID = 5431419815908440802L;

    public UnitFunction(){
        super(1);
    }
}
