package Exceptions;

/**
 * Created by loren on 06/09/2016.
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
