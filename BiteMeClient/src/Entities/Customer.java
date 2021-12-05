package Entities;

public class Customer extends User {

	/**
	 * Serializable autogenerated serialversion.
	 */
	private static final long serialVersionUID = -4046991480431316453L;

	public Customer(String userName, String password, String firstName, String lastName, String id, String email,
			String phoneNumber, Role role, String organization, Branch mainBranch) {
		super(userName, password, firstName, lastName, id, email, phoneNumber, role, organization, mainBranch);
	}
	
}