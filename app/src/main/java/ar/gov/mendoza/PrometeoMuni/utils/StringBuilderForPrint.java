package ar.gov.mendoza.PrometeoMuni.utils;



public class StringBuilderForPrint {
	StringBuilder mStrintBuilder;
	public StringBuilderForPrint(){
		mStrintBuilder = new StringBuilder();
	}

	public StringBuilderForPrint append(String str)
	{
		this.mStrintBuilder.append(str).append("\r\n");
		return this;
	}
	
	@Override 
	public String toString()
	{
		return this.mStrintBuilder.toString();
	}
	
}
