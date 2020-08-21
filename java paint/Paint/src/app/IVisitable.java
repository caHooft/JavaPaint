package app;

//Visitor Pattern
//Define an interface to repressent element
interface IVisitable 
{
    public void accept(IVisitor visitor);
    //public Shape accept(IVisitor visitor);
    //public String accept(IVisitor visitor);
    //public double accept(IVisitor visitor);

}