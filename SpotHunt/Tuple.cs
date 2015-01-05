public class Tuple<T1, T2, T3> {
	public T1 First {get; private set;}
	public T2 Second {get; private set;}
	public T3 Third {get; private set;}

	internal Tuple(T1 first, T2 second, T3 third)
	{
		First = first;
		Second = second;
		Third = third;
	}
}

public static class Tuple
{
	public static Tuple<T1, T2, T3> New<T1, T2, T3>(T1 first, T2 second, T3 third)
	{
		var tuple = new Tuple<T1, T2, T3>(first, second, third);
		return tuple;
	}
}