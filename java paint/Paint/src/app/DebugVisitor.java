package app;

class DebugVisitor implements IVisitor 
{
    public DebugVisitor()     
    {

    }

    public String visit(Circle circle) 
    {
        
        System.out.println("circle!!");
        return String.valueOf("circle");
        
    }
        
    public String visit(Rectangle rectangle) 
    {
        
        System.out.println("rectangle!!");
        return String.valueOf("rectangle");
        
    }
}
        