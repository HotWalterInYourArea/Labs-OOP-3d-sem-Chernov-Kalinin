package functions;

import ui.annotations.SimpleFunction;

import java.io.Serial;

@SimpleFunction(canonName="Нулевая функция")
public class ZeroFunction extends ConstantFunction {
    @Serial
    private static final long serialVersionUID = 2002981549414092683L;

    public ZeroFunction(){
        super(0);

    }

}
