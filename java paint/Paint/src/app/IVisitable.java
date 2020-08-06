package app;

//Visitor Pattern
//Define an interface to repressent element
interface IVisitable 
{
    public String accept(IVisitor visitor);
    //public double accept(IVisitor visitor);

}