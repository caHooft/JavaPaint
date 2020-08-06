package app;

//interface for all the shapes
//this interface demands a drawing method and a add text method

//this is the start of my composite pattern
//I make a common interface which declares things i need for all shapes
//in my composite pattern i use base shape as a container class/composite class
//base shape in this project represents my complex elements
//base shape delegates most f the work to his sub elements
//sub elements of base shape are circle and rectangle

public interface IShapes
{
    void draw(int sx, int sy, int ex, int ey);
    void addText(String position, String text);
}