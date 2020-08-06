package app;

import java.awt.*;

//This is a interface that is part of my strategy pattern
//In the way im using it it is still reuseable here
//I mean by that that multiple other algorithms for resizing diffrent shapes can implement this
//I however only have 2 shapes so i am implementing it twice with two diffrent implementations
//these two implementations are :ResizeCircleStrategy and ResizeRectangleStrategy
//Those two normally would also be used by multiple classes to optimise the use of this pattern
//I however dont do that since i only have two kinds of shapes

public interface IResizeStrategy
{
    void resize(Shape shape, int width, int height);
}