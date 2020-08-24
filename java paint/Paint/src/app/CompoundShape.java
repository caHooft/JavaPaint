package app;

import java.awt.geom.* ; 
import java.awt.* ; 
import java.util.* ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundShape extends BaseShape
{

    public CompoundShape(IDrawStrategy drawBehaviour, IMoveStrategy moveBehaviour, IResizeStrategy resizeBehaviour,Shape shape) 
    {
        super(drawBehaviour, moveBehaviour, resizeBehaviour, shape);
        // TODO Auto-generated constructor stub
    }

	@Override
    public BaseShape clone() 
    {
		// TODO Auto-generated method stub
		return null;
	}
}