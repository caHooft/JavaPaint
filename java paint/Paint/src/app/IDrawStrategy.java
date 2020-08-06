package app;

import java.awt.Shape;

//This is a interface that is part of my strategy pattern
//In the way im using it it is still reuseable here
//I mean by that that multiple other algorithms for drawing shapes can implement this
//I however only have 2 shapes so i am implementing it twice with two diffrent implementations
//these two implementations are :DrawCircleStrategy and DrawRectangleStrategy
//Those two normally would also be used by multiple classes to optimise the use of this pattern
//I however dont do that since i only have two kinds of shapes

public interface IDrawStrategy
{
    public void draw(Shape shape, int sx, int sy, int ex, int ey);
}