package app;

//Visitor Pattern
//visitor pattern allows adding of methods to classes without altering those classes in a mayor way
//depending on the class diffrent mehtods can be used
//allows me to define a external class that extends a class without mayor changes to said class that i am extending 

//Create visitor interface
interface IVisitor 
{
	//here i accept diffrent kinds of objects within my visitor
	public String visit(Circle circle);
	public String visit(Rectangle rectangle);
	//public double visit(Liquor liquorItem);
	//public double visit(Tobacco tobaccoItem);
	//public double visit(Necessity necessityItem);
    
}