package Exceptions;

/**
 * Exception used to tell the user that the requirements of the domain doesn't contain the requirements of the problem
 */
public class RequirementException extends Exception {
	String wrongRequirement = "";
	public RequirementException(String wrongRequirement){
		this.wrongRequirement = wrongRequirement;
	}

	@Override
	public String getLocalizedMessage() {
		return "Problem requirement not contained in domain requirements:"+wrongRequirement;
	}

	@Override
	public String getMessage() {
		return "Problem requirement not contained in domain requirements:"+wrongRequirement;
	}
}
