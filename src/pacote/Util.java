package pacote;

public class Util 
{
	public static long getTempo()
	{
		return System.currentTimeMillis();
	}
	
	public static long getDiferenca(long tempoInicial)
	{
		return System.currentTimeMillis() - tempoInicial;
	}
	
	public static void printThread(String conteudo)
	{
		System.out.println("T"+Thread.currentThread().getName() + ": " + conteudo);
	}
}
