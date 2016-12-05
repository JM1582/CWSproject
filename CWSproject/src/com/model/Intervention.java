package com.model;

public enum Intervention {
	DE {
		@Override
		public String toString() {
			return "Germany";
		}
	},
	IT {
		@Override
	    public String toString() {
	      return "Italy";
	    }
	  },
	  US {
	    @Override
	    public String toString() {
	      return "United States";
	    }
	  }
}
