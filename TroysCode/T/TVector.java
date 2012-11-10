package TroysCode.T;

public class TVector
	{
		public double x;
		public double y;
		public double z;

		public TVector(double x, double y)
			{
				this.x = x;
				this.y = y;
				z = 0;
			}

		public TVector(double x, double y, double z)
			{
				this.x = x;
				this.y = y;
				this.z = z;
			}

		/**
		 * Sets this vector to the vector cross product of vectors v1 and v2.
		 * 
		 * @param v1
		 *            the first vector
		 * @param v2
		 *            the second vector
		 */
		public final void cross(TVector v1, TVector v2)
			{
				double x, y;

				x = v1.y * v2.z - v1.z * v2.y;
				y = v2.x * v1.z - v2.z * v1.x;
				this.z = v1.x * v2.y - v1.y * v2.x;
				this.x = x;
				this.y = y;
			}

		/**
		 * Sets the value of this vector to the normalisation of vector v1.
		 * 
		 * @param v1
		 *            the un-normalised vector
		 */
		public final void normalise(TVector v1)
			{
				double norm;

				norm = 1.0 / Math.sqrt((v1.x * v1.x) + (v1.y * v1.y) + (v1.z * v1.z));
				x = v1.x * norm;
				y = v1.y * norm;
				z = v1.z * norm;
			}

		/**
		 * Normalises this vector in place.
		 */
		public final void normalise()
			{
				double norm;

				norm = 1.0 / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
				this.x *= norm;
				this.y *= norm;
				this.z *= norm;
			}

		/**
		 * @param v
		 *            - The other {@link TVector} with which the dot product is
		 *            being calculated with.
		 * @return A double, representing the dot product of this vecor and
		 *         another.
		 */
		public final double getDotProduct(TVector v)
			{
				return (x * v.x) + (y * v.y) + (z * v.z);
			}

		/**
		 * Returns the squared length of this vector.
		 * 
		 * @return the squared length of this vector
		 */
		public final double getLengthSquared()
			{
				return (this.x * this.x + this.y * this.y + this.z * this.z);
			}

		/**
		 * Returns the length of this vector.
		 * 
		 * @return the length of this vector
		 */
		public final double getLength()
			{
				return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
			}

		/**
		 * Returns the angle in degrees between this vector and the vector
		 * parameter; the return value is constrained to the range [0,PI].
		 * 
		 * @param v1
		 *            the other vector
		 * @return the angle in radians in the range [0,PI]
		 */
		public final double getAngle(TVector v1)
			{
				double vDot = this.getDotProduct(v1) / (getLength() * v1.getLength());
				if (vDot < -1.0)
					vDot = -1.0;
				if (vDot > 1.0)
					vDot = 1.0;
				return Math.toDegrees(Math.acos(vDot));
			}
	}