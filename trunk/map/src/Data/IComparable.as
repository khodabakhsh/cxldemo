package Data
{
	public interface IComparable
	{
		 /**
         * Compare this object with rhs.
         * @param rhs the second Comparable.
         * @return 0 if two objects are equal;
         *     less than zero if this object is smaller;
         *     greater than zero if this object is larger.
         */
        function compareTo(rhs:IComparable):int;
	}
}