package Exceptions;

/**
 * Created by loren on 06/09/2016.
 */
public class WrongDomainException extends Exception {
	String first;
	String second;
	public WrongDomainException(String first, String second){
		this.first = first;
		this.second = second;
	}

	@Override
	public String getLocalizedMessage() {
		return "Domains do not match -> 1:"+first+", 2:"+second;
	}

	@Override
	public String getMessage() {
		return "The two input domain are different -> 1:"+first+", 2:"+second;
	}
}
