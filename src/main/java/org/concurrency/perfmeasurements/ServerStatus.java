/**
 * 
 */
package org.concurrency.perfmeasurements;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that holds a set of {@link User}s and a set of {@link Query}s. It
 * also provides methods for addition/removal of users and queries, which use
 * lock splitting technique as well as those which do not.
 * 
 * @author Abhinav Tripathi
 */
public class ServerStatus {

	private Set<User> users = new HashSet<ServerStatus.User>();
	private Set<Query> queries = new HashSet<ServerStatus.Query>();

	public synchronized void slowAddUser(User u) {
		users.add(u);
	}

	public void fastAddUser(User u) {
		synchronized (users) {
			users.add(u);
		}
	}

	public synchronized void slowAddQuery(Query q) {
		queries.add(q);
	}

	public synchronized void fastAddQuery(Query q) {
		queries.add(q);
	}

	public synchronized void slowRemoveUser() {
		while (users.iterator().hasNext())
			users.remove(users.iterator().next());
	}

	public void fastRemoveUser() {
		synchronized (users) {
			while (users.iterator().hasNext())
				users.remove(users.iterator().next());
		}
	}

	public synchronized void slowRemoveQuery() {
		while (queries.iterator().hasNext())
			queries.remove(queries.iterator().next());
	}

	public void fastRemoveQuery() {
		synchronized (queries) {
			while (queries.iterator().hasNext())
				queries.remove(queries.iterator().next());
		}
	}

	/**
	 * A simple class representing a user.
	 * 
	 * @author abhinav
	 */
	public class User {
		private final String name;
		private final int age;

		public User(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + age;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (age != other.age)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		private ServerStatus getOuterType() {
			return ServerStatus.this;
		}
	}

	/**
	 * A simple class representing a query.
	 * 
	 * @author abhinav
	 */
	public class Query {
		private final String query;

		public Query(String query) {
			this.query = query;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((query == null) ? 0 : query.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Query other = (Query) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (query == null) {
				if (other.query != null)
					return false;
			} else if (!query.equals(other.query))
				return false;
			return true;
		}

		private ServerStatus getOuterType() {
			return ServerStatus.this;
		}
	}

}
