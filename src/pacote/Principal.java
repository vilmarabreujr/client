package pacote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Principal 
{
	static 
	{
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) 
	        {
	            if (hostname.equals("domain-a") || hostname.equals("domain-b") || hostname.equals("domain-c")) 
	            {
	                return true;
	            }
	            return false;
	        }
	    });
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub	
		
		int TotalClientes = 1;	
		int NumeroRequisicoes = 1;
		boolean sra_enable = true;
		
		if( args.length > 0 )
		{
			TotalClientes = Integer.parseInt(args[0]);
			NumeroRequisicoes = Integer.parseInt(args[1]);
			sra_enable = Boolean.parseBoolean(args[2]);
		}

		List<String> Lista = getListaUsuarios();
		
				
		
		boolean CarregarAmbiente = false;
		
		if( CarregarAmbiente )
		{
			Ambiente ambiente = new Ambiente();
			ambiente.CarregarUsuarios(Lista);
		}
		else
		{
			List<Integer> ListaNumerica = new ArrayList<Integer>();
			Random randomGenerator = new Random();

			while( ListaNumerica.size() != TotalClientes )
			{
			      int randomInt = randomGenerator.nextInt(Lista.size());
			      if( !ListaNumerica.contains(randomInt) )
			      {
			    	  ListaNumerica.add(randomInt);
			      }
			}
			
			List<Usuario> ListaUsuario = new ArrayList<Usuario>();
			for( int i = 0; i < ListaNumerica.size(); i++ )
			{
				int indice = ListaNumerica.get(i);
				ListaUsuario.add(new Usuario(Lista.get(indice)));	
			}
			
			long tempoInicial = Util.getTempo();

			List<Processo> ListaProcessos = new ArrayList<Processo>();
			try
			{
				for( int i = 0; i < ListaUsuario.size(); i++ )
				{
					Processo p = new Processo(ListaUsuario.get(i),NumeroRequisicoes,sra_enable);
					ListaProcessos.add(p);
					p.start();
				}
				
				for( int i = 0; i < ListaProcessos.size(); i++ )
				{
					Processo p = ListaProcessos.get(i);
					p.join();
				}
				
				
			}
			catch( Exception e )
			{
				System.out.println(e.getMessage());
			}
			
			boolean logInterno = false;
			
			if (logInterno )
			{
				System.out.println("Tempo total de execucao para " + TotalClientes + " usuários realizando " + NumeroRequisicoes + " requisições cada: " + Util.getDiferenca(tempoInicial));
				
				for( int i = 0; i < ListaProcessos.size(); i++ )
				{
					Processo p = ListaProcessos.get(i);
					System.out.println("----------------");
					p.MostrarImpressao(Integer.toString(i));
				}
			}
			else
			{
				try 
				{
					long tempoFinal = Util.getDiferenca(tempoInicial);
					String content = "Clientes;Requisicoes;SRA\n";
					content += TotalClientes + ";" + NumeroRequisicoes + ";" + sra_enable+";";
					System.out.print(TotalClientes + ";" + NumeroRequisicoes + ";"+ sra_enable+";");
					
					content += "\n\n";
					String nomeArquivo = "c"+TotalClientes+"r"+NumeroRequisicoes+".csv";
					if( sra_enable )
						nomeArquivo = "sra_" + nomeArquivo;
		            File newTextFile = new File("/home/vilmar/Desktop/Executavel/Out/" + nomeArquivo);
		            FileWriter fileWriter = new FileWriter(newTextFile);
		            
		            List<String> tempos = new ArrayList<String>();
		            for( int i = 0; i < ListaProcessos.size(); i++ )
					{
						Processo p = ListaProcessos.get(i);
						tempos.add(p.TextoConsumo());
						content += p.TextoCSV(Integer.toString(i));
					}
		            fileWriter.write(content);
		            fileWriter.close();
		            
		            float a = 0;
		            float b = 0;
		            float c = 0;
		            float d = 0;
		            float e = 0;
		            float f = 0;
		            float g = 0;
		            float h = 0;
		            float i = 0;
		            float j = 0;
		            float k = 0;
		            
		            for( int z = 0; z < tempos.size(); z++ )
					{
		            	String valores = tempos.get(z);
		            	a += Float.parseFloat(valores.split(";")[0]);
		            	b += Float.parseFloat(valores.split(";")[1]);
		            	c += Float.parseFloat(valores.split(";")[2]);
		            	d += Float.parseFloat(valores.split(";")[3]);
		            	e += Float.parseFloat(valores.split(";")[4]);
		            	f += Float.parseFloat(valores.split(";")[5]);
		            	g += Float.parseFloat(valores.split(";")[6]);
		            	h += Float.parseFloat(valores.split(";")[7]);
		            	i += Float.parseFloat(valores.split(";")[8]);
		            	j += Float.parseFloat(valores.split(";")[9]);
		            	k += Float.parseFloat(valores.split(";")[10]);
					}
		            a = a/tempos.size();
		            b = b/tempos.size();
		            c = c/tempos.size();
		            d = d/tempos.size();
		            e = e/tempos.size();
		            f = f/tempos.size();
		            g = g/tempos.size();
		            h = h/tempos.size();
		            i = i/tempos.size();
		            j = j/tempos.size();
		            k = k/tempos.size();
		            System.out.print(String.format("%.2f",a).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",b).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",c).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",d).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",e).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",f).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",g).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",h).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",i).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",j).replace(".", ",") + ";");
		            System.out.print(String.format("%.2f",k).replace(".", ",") + ";");
		            System.out.println(tempoFinal);
					
		            
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
	}
	
	public static List<String> getListaUsuarios()
	{
		List<String> Lista = new ArrayList<String>();
		Lista.add("alice");
		Lista.add("bob");
		Lista.add("claire");
		Lista.add("dan");
		Lista.add("elmo");
		Lista.add("francesca");
		Lista.add("gabriel");
		Lista.add("hazel");
		Lista.add("iris");
		Lista.add("jenny");
		Lista.add("katie");
		Lista.add("lee");
		Lista.add("maurice");
		Lista.add("nicole");
		Lista.add("oliver");
		Lista.add("otavio");
		Lista.add("pavel");
		Lista.add("quasim");
		Lista.add("rebecca");
		Lista.add("sam");
		Lista.add("tim");
		Lista.add("uriel");
		Lista.add("vabreu");
		Lista.add("xandra");
		Lista.add("yves");
		Lista.add("zoe");
		Lista.add("alice_junior");
		Lista.add("bob_junior");
		Lista.add("claire_junior");
		Lista.add("dan_junior");
		Lista.add("elmo_junior");
		Lista.add("francesca_junior");
		Lista.add("gabriel_junior");
		Lista.add("hazel_junior");
		Lista.add("iris_junior");
		Lista.add("jenny_junior");
		Lista.add("katie_junior");
		Lista.add("lee_junior");
		Lista.add("maurice_junior");
		Lista.add("nicole_junior");
		Lista.add("oliver_junior");
		Lista.add("otavio_junior");
		Lista.add("pavel_junior");
		Lista.add("quasim_junior");
		Lista.add("rebecca_junior");
		Lista.add("sam_junior");
		Lista.add("tim_junior");
		Lista.add("uriel_junior");
		Lista.add("vabreu_junior");
		Lista.add("vilmar_junior");
		Lista.add("xandra_junior");
		Lista.add("yves_junior");
		Lista.add("zoe_junior");
		Lista.add("rosada");
		Lista.add("achocolatada");
		Lista.add("grelo");
		Lista.add("choc_turbo");
		Lista.add("greicy");
		Lista.add("vesga");
		Lista.add("rosada_jr");
		Lista.add("minhoca");
		Lista.add("favelada");
		Lista.add("jenny_abreu");
		Lista.add("katie_abreu");
		Lista.add("lee_abreu");
		Lista.add("maurice_abreu");
		Lista.add("nicole_abreu");
		Lista.add("oliver_abreu");
		Lista.add("otavio_abreu");
		Lista.add("pavel_abreu");
		Lista.add("quasim_abreu");
		Lista.add("rebecca_abreu");
		Lista.add("sam_abreu");
		Lista.add("tim_abreu");
		Lista.add("uriel_abreu");
		Lista.add("vabreu_abreu");
		Lista.add("xandra_abreu");
		Lista.add("yves_abreu");
		Lista.add("zoe_abreu");
		Lista.add("alice_junior_abreu");
		Lista.add("bob_junior_abreu");
		Lista.add("claire_junior_abreu");
		Lista.add("dan_junior_abreu");
		Lista.add("elmo_junior_abreu");
		Lista.add("francesca_junior_abreu");
		Lista.add("gabriel_junior_abreu");
		Lista.add("hazel_junior_abreu");
		Lista.add("iris_junior_abreu");
		Lista.add("jenny_junior_abreu");
		Lista.add("katie_junior_abreu");
		Lista.add("lee_junior_abreu");
		Lista.add("maurice_junior_abreu");
		Lista.add("nicole_junior_abreu");
		Lista.add("oliver_junior_abreu");
		Lista.add("otavio_junior_abreu");
		Lista.add("pavel_junior_abreu");
		Lista.add("quasim_junior_abreu");
		Lista.add("rebecca_junior_abreu");
		Lista.add("sam_junior_abreu");
		Lista.add("tim_junior_abreu");
		Lista.add("uriel_junior_abreu");
		Lista.add("vabreu_junior_abreu");
		Lista.add("vilmar_junior_abreu");
		Lista.add("xandra_junior_abreu");
		Lista.add("yves_junior_abreu");
		Lista.add("zoe_junior_abreu");
		Lista.add("alicez");
		Lista.add("bobz");
		Lista.add("clairez");
		Lista.add("danz");
		Lista.add("elmoz");
		Lista.add("francescaz");
		Lista.add("gabrielz");
		Lista.add("hazelz");
		Lista.add("irisz");
		Lista.add("jennyz");
		Lista.add("katiez");
		Lista.add("leez");
		Lista.add("mauricez");
		Lista.add("nicolez");
		Lista.add("oliverz");
		Lista.add("otavioz");
		Lista.add("pavelz");
		Lista.add("quasimz");
		Lista.add("rebeccaz");
		Lista.add("samz");
		Lista.add("timz");
		Lista.add("urielz");
		Lista.add("vabreuz");
		Lista.add("xandraz");
		Lista.add("yvesz");
		Lista.add("zoez");
		Lista.add("alice_juniorz");
		Lista.add("bob_juniorz");
		Lista.add("claire_juniorz");
		Lista.add("dan_juniorz");
		Lista.add("elmo_juniorz");
		Lista.add("francesca_juniorz");
		Lista.add("gabriel_juniorz");
		Lista.add("hazel_juniorz");
		Lista.add("iris_juniorz");
		Lista.add("jenny_juniorz");
		Lista.add("katie_juniorz");
		Lista.add("lee_juniorz");
		Lista.add("maurice_juniorz");
		Lista.add("nicole_juniorz");
		Lista.add("oliver_juniorz");
		Lista.add("otavio_juniorz");
		Lista.add("pavel_juniorz");
		Lista.add("quasim_juniorz");
		Lista.add("rebecca_juniorz");
		Lista.add("sam_juniorz");
		Lista.add("tim_juniorz");
		Lista.add("uriel_juniorz");
		Lista.add("vabreu_juniorz");
		Lista.add("vilmar_juniorz");
		Lista.add("xandra_juniorz");
		Lista.add("yves_juniorz");
		Lista.add("zoe_juniorz");
		Lista.add("rosadaz");
		Lista.add("achocolatadaz");
		Lista.add("greloz");
		Lista.add("choc_turboz");
		Lista.add("greicyz");
		Lista.add("vesgaz");
		Lista.add("rosada_jrz");
		Lista.add("minhocaz");
		Lista.add("faveladaz");
		Lista.add("jenny_abreuz");
		Lista.add("katie_abreuz");
		Lista.add("lee_abreuz");
		Lista.add("maurice_abreuz");
		Lista.add("nicole_abreuz");
		Lista.add("oliver_abreuz");
		Lista.add("otavio_abreuz");
		Lista.add("pavel_abreuz");
		Lista.add("quasim_abreuz");
		Lista.add("rebecca_abreuz");
		Lista.add("sam_abreuz");
		Lista.add("tim_abreuz");
		Lista.add("uriel_abreuz");
		Lista.add("vabreu_abreuz");
		Lista.add("xandra_abreuz");
		Lista.add("yves_abreuz");
		Lista.add("zoe_abreuz");
		Lista.add("alice_junior_abreuz");
		Lista.add("bob_junior_abreuz");
		Lista.add("claire_junior_abreuz");
		Lista.add("dan_junior_abreuz");
		Lista.add("elmo_junior_abreuz");
		Lista.add("francesca_junior_abreuz");
		Lista.add("gabriel_junior_abreuz");
		Lista.add("hazel_junior_abreuz");
		Lista.add("iris_junior_abreuz");
		Lista.add("jenny_junior_abreuz");
		Lista.add("katie_junior_abreuz");
		Lista.add("lee_junior_abreuz");
		Lista.add("maurice_junior_abreuz");
		Lista.add("nicole_junior_abreuz");
		Lista.add("oliver_junior_abreuz");
		Lista.add("otavio_junior_abreuz");
		Lista.add("pavel_junior_abreuz");
		Lista.add("quasim_junior_abreuz");
		Lista.add("rebecca_junior_abreuz");
		Lista.add("sam_junior_abreuz");
		Lista.add("tim_junior_abreuz");
		Lista.add("uriel_junior_abreuz");
		Lista.add("vabreu_junior_abreuz");
		Lista.add("vilmar_junior_abreuz");
		Lista.add("xandra_junior_abreuz");
		Lista.add("yves_junior_abreuz");
		Lista.add("zoe_junior_abreuz");
		Lista.add("alicezt");
		Lista.add("bobzt");
		Lista.add("clairezt");
		Lista.add("danzt");
		Lista.add("elmozt");
		Lista.add("francescazt");
		Lista.add("gabrielzt");
		Lista.add("hazelzt");
		Lista.add("iriszt");
		Lista.add("jennyzt");
		Lista.add("katiezt");
		Lista.add("leezt");
		Lista.add("mauricezt");
		Lista.add("nicolezt");
		Lista.add("oliverzt");
		Lista.add("otaviozt");
		Lista.add("pavelzt");
		Lista.add("quasimzt");
		Lista.add("rebeccazt");
		Lista.add("samzt");
		Lista.add("timzt");
		Lista.add("urielzt");
		Lista.add("vabreuzt");
		Lista.add("xandrazt");
		Lista.add("yveszt");
		Lista.add("zoezt");
		Lista.add("alice_juniorzt");
		Lista.add("bob_juniorzt");
		Lista.add("claire_juniorzt");
		Lista.add("dan_juniorzt");
		Lista.add("elmo_juniorzt");
		Lista.add("francesca_juniorzt");
		Lista.add("gabriel_juniorzt");
		Lista.add("hazel_juniorzt");
		Lista.add("iris_juniorzt");
		Lista.add("jenny_juniorzt");
		Lista.add("katie_juniorzt");
		Lista.add("lee_juniorzt");
		Lista.add("maurice_juniorzt");
		Lista.add("nicole_juniorzt");
		Lista.add("oliver_juniorzt");
		Lista.add("otavio_juniorzt");
		Lista.add("pavel_juniorzt");
		Lista.add("quasim_juniorzt");
		Lista.add("rebecca_juniorzt");
		Lista.add("sam_juniorzt");
		Lista.add("tim_juniorzt");
		Lista.add("uriel_juniorzt");
		Lista.add("vabreu_juniorzt");
		Lista.add("vilmar_juniorzt");
		Lista.add("xandra_juniorzt");
		Lista.add("yves_juniorzt");
		Lista.add("zoe_juniorzt");
		Lista.add("rosadazt");
		Lista.add("achocolatadazt");
		Lista.add("grelozt");
		Lista.add("choc_turbozt");
		Lista.add("greicyzt");
		Lista.add("vesgazt");
		Lista.add("rosada_jrzt");
		Lista.add("minhocazt");
		Lista.add("faveladazt");
		Lista.add("jenny_abreuzt");
		Lista.add("katie_abreuzt");
		Lista.add("lee_abreuzt");
		Lista.add("maurice_abreuzt");
		Lista.add("nicole_abreuzt");
		Lista.add("oliver_abreuzt");
		Lista.add("otavio_abreuzt");
		Lista.add("pavel_abreuzt");
		Lista.add("quasim_abreuzt");
		Lista.add("rebecca_abreuzt");
		Lista.add("sam_abreuzt");
		Lista.add("tim_abreuzt");
		Lista.add("uriel_abreuzt");
		Lista.add("vabreu_abreuzt");
		Lista.add("xandra_abreuzt");
		Lista.add("yves_abreuzt");
		Lista.add("zoe_abreuzt");
		Lista.add("alice_junior_abreuzt");
		Lista.add("bob_junior_abreuzt");
		Lista.add("claire_junior_abreuzt");
		Lista.add("dan_junior_abreuzt");
		Lista.add("elmo_junior_abreuzt");
		Lista.add("francesca_junior_abreuzt");
		Lista.add("gabriel_junior_abreuzt");
		Lista.add("hazel_junior_abreuzt");
		Lista.add("iris_junior_abreuzt");
		Lista.add("jenny_junior_abreuzt");
		Lista.add("katie_junior_abreuzt");
		Lista.add("lee_junior_abreuzt");
		Lista.add("maurice_junior_abreuzt");
		Lista.add("nicole_junior_abreuzt");
		Lista.add("oliver_junior_abreuzt");
		Lista.add("otavio_junior_abreuzt");
		Lista.add("pavel_junior_abreuzt");
		Lista.add("quasim_junior_abreuzt");
		Lista.add("rebecca_junior_abreuzt");
		Lista.add("sam_junior_abreuzt");
		Lista.add("tim_junior_abreuzt");
		Lista.add("uriel_junior_abreuzt");
		Lista.add("vabreu_junior_abreuzt");
		Lista.add("vilmar_junior_abreuzt");
		Lista.add("xandra_junior_abreuzt");
		Lista.add("yves_junior_abreuzt");
		Lista.add("zoe_junior_abreuzt");
		Lista.add("aliceztv");
		Lista.add("bobztv");
		Lista.add("claireztv");
		Lista.add("danztv");
		Lista.add("elmoztv");
		Lista.add("francescaztv");
		Lista.add("gabrielztv");
		Lista.add("hazelztv");
		Lista.add("irisztv");
		Lista.add("jennyztv");
		Lista.add("katieztv");
		Lista.add("leeztv");
		Lista.add("mauriceztv");
		Lista.add("nicoleztv");
		Lista.add("oliverztv");
		Lista.add("otavioztv");
		Lista.add("pavelztv");
		Lista.add("quasimztv");
		Lista.add("rebeccaztv");
		Lista.add("samztv");
		Lista.add("timztv");
		Lista.add("urielztv");
		Lista.add("vabreuztv");
		Lista.add("xandraztv");
		Lista.add("yvesztv");
		Lista.add("zoeztv");
		Lista.add("alice_juniorztv");
		Lista.add("bob_juniorztv");
		Lista.add("claire_juniorztv");
		Lista.add("dan_juniorztv");
		Lista.add("elmo_juniorztv");
		Lista.add("francesca_juniorztv");
		Lista.add("gabriel_juniorztv");
		Lista.add("hazel_juniorztv");
		Lista.add("iris_juniorztv");
		Lista.add("jenny_juniorztv");
		Lista.add("katie_juniorztv");
		Lista.add("lee_juniorztv");
		Lista.add("maurice_juniorztv");
		Lista.add("nicole_juniorztv");
		Lista.add("oliver_juniorztv");
		Lista.add("otavio_juniorztv");
		Lista.add("pavel_juniorztv");
		Lista.add("quasim_juniorztv");
		Lista.add("rebecca_juniorztv");
		Lista.add("sam_juniorztv");
		Lista.add("tim_juniorztv");
		Lista.add("uriel_juniorztv");
		Lista.add("vabreu_juniorztv");
		Lista.add("vilmar_juniorztv");
		Lista.add("xandra_juniorztv");
		Lista.add("yves_juniorztv");
		Lista.add("zoe_juniorztv");
		Lista.add("rosadaztv");
		Lista.add("achocolatadaztv");
		Lista.add("greloztv");
		Lista.add("choc_turboztv");
		Lista.add("greicyztv");
		Lista.add("vesgaztv");
		Lista.add("rosada_jrztv");
		Lista.add("minhocaztv");
		Lista.add("faveladaztv");
		Lista.add("jenny_abreuztv");
		Lista.add("katie_abreuztv");
		Lista.add("lee_abreuztv");
		Lista.add("maurice_abreuztv");
		Lista.add("nicole_abreuztv");
		Lista.add("oliver_abreuztv");
		Lista.add("otavio_abreuztv");
		Lista.add("pavel_abreuztv");
		Lista.add("quasim_abreuztv");
		Lista.add("rebecca_abreuztv");
		Lista.add("sam_abreuztv");
		Lista.add("tim_abreuztv");
		Lista.add("uriel_abreuztv");
		Lista.add("vabreu_abreuztv");
		Lista.add("xandra_abreuztv");
		Lista.add("yves_abreuztv");
		Lista.add("zoe_abreuztv");
		Lista.add("alice_junior_abreuztv");
		Lista.add("bob_junior_abreuztv");
		Lista.add("claire_junior_abreuztv");
		Lista.add("dan_junior_abreuztv");
		Lista.add("elmo_junior_abreuztv");
		Lista.add("francesca_junior_abreuztv");
		Lista.add("gabriel_junior_abreuztv");
		Lista.add("hazel_junior_abreuztv");
		Lista.add("iris_junior_abreuztv");
		Lista.add("jenny_junior_abreuztv");
		Lista.add("katie_junior_abreuztv");
		Lista.add("lee_junior_abreuztv");
		Lista.add("maurice_junior_abreuztv");
		Lista.add("nicole_junior_abreuztv");
		Lista.add("oliver_junior_abreuztv");
		Lista.add("otavio_junior_abreuztv");
		Lista.add("pavel_junior_abreuztv");
		Lista.add("quasim_junior_abreuztv");
		Lista.add("rebecca_junior_abreuztv");
		Lista.add("sam_junior_abreuztv");
		Lista.add("tim_junior_abreuztv");
		Lista.add("uriel_junior_abreuztv");
		Lista.add("vabreu_junior_abreuztv");
		Lista.add("vilmar_junior_abreuztv");
		Lista.add("xandra_junior_abreuztv");
		Lista.add("yves_junior_abreuztv");
		Lista.add("zoe_junior_abreuztv");
		Lista.add("aliceztvw");
		Lista.add("bobztvw");
		Lista.add("claireztvw");
		Lista.add("danztvw");
		Lista.add("elmoztvw");
		Lista.add("francescaztvw");
		Lista.add("gabrielztvw");
		Lista.add("hazelztvw");
		Lista.add("irisztvw");
		Lista.add("jennyztvw");
		Lista.add("katieztvw");
		Lista.add("leeztvw");
		Lista.add("mauriceztvw");
		Lista.add("nicoleztvw");
		Lista.add("oliverztvw");
		Lista.add("otavioztvw");
		Lista.add("pavelztvw");
		Lista.add("quasimztvw");
		Lista.add("rebeccaztvw");
		Lista.add("samztvw");
		Lista.add("timztvw");
		Lista.add("urielztvw");
		Lista.add("vabreuztvw");
		Lista.add("xandraztvw");
		Lista.add("yvesztvw");
		Lista.add("zoeztvw");
		Lista.add("alice_juniorztvw");
		Lista.add("bob_juniorztvw");
		Lista.add("claire_juniorztvw");
		Lista.add("dan_juniorztvw");
		Lista.add("elmo_juniorztvw");
		Lista.add("francesca_juniorztvw");
		Lista.add("gabriel_juniorztvw");
		Lista.add("hazel_juniorztvw");
		Lista.add("iris_juniorztvw");
		Lista.add("jenny_juniorztvw");
		Lista.add("katie_juniorztvw");
		Lista.add("lee_juniorztvw");
		Lista.add("maurice_juniorztvw");
		Lista.add("nicole_juniorztvw");
		Lista.add("oliver_juniorztvw");
		Lista.add("otavio_juniorztvw");
		Lista.add("pavel_juniorztvw");
		Lista.add("quasim_juniorztvw");
		Lista.add("rebecca_juniorztvw");
		Lista.add("sam_juniorztvw");
		Lista.add("tim_juniorztvw");
		Lista.add("uriel_juniorztvw");
		Lista.add("vabreu_juniorztvw");
		Lista.add("vilmar_juniorztvw");
		Lista.add("xandra_juniorztvw");
		Lista.add("yves_juniorztvw");
		Lista.add("zoe_juniorztvw");
		Lista.add("rosadaztvw");
		Lista.add("achocolatadaztvw");
		Lista.add("greloztvw");
		Lista.add("choc_turboztvw");
		Lista.add("greicyztvw");
		Lista.add("vesgaztvw");
		Lista.add("rosada_jrztvw");
		Lista.add("minhocaztvw");
		Lista.add("faveladaztvw");
		Lista.add("jenny_abreuztvw");
		Lista.add("katie_abreuztvw");
		Lista.add("lee_abreuztvw");
		Lista.add("maurice_abreuztvw");
		Lista.add("nicole_abreuztvw");
		Lista.add("oliver_abreuztvw");
		Lista.add("otavio_abreuztvw");
		Lista.add("pavel_abreuztvw");
		Lista.add("quasim_abreuztvw");
		Lista.add("rebecca_abreuztvw");
		Lista.add("tim_abreuztvw");
		Lista.add("uriel_abreuztvw");
		Lista.add("vabreu_abreuztvw");
		Lista.add("xandra_abreuztvw");
		Lista.add("yves_abreuztvw");
		Lista.add("zoe_abreuztvw");
		Lista.add("alice_junior_abreuztvw");
		Lista.add("bob_junior_abreuztvw");
		Lista.add("claire_junior_abreuztvw");
		Lista.add("dan_junior_abreuztvw");
		Lista.add("elmo_junior_abreuztvw");
		Lista.add("francesca_junior_abreuztvw");
		Lista.add("gabriel_junior_abreuztvw");
		Lista.add("hazel_junior_abreuztvw");
		Lista.add("iris_junior_abreuztvw");
		Lista.add("jenny_junior_abreuztvw");
		Lista.add("katie_junior_abreuztvw");
		Lista.add("lee_junior_abreuztvw");
		Lista.add("maurice_junior_abreuztvw");
		Lista.add("nicole_junior_abreuztvw");
		Lista.add("oliver_junior_abreuztvw");
		Lista.add("otavio_junior_abreuztvw");
		Lista.add("pavel_junior_abreuztvw");
		Lista.add("quasim_junior_abreuztvw");
		Lista.add("rebecca_junior_abreuztvw");
		Lista.add("sam_junior_abreuztvw");
		Lista.add("tim_junior_abreuztvw");
		Lista.add("uriel_junior_abreuztvw");
		Lista.add("vabreu_junior_abreuztvw");
		Lista.add("vilmar_junior_abreuztvw");
		Lista.add("xandra_junior_abreuztvw");
		Lista.add("yves_junior_abreuztvw");
		Lista.add("zoe_junior_abreuztvw");
		Lista.add("aliceztvwq");
		Lista.add("bobztvwq");
		Lista.add("claireztvwq");
		Lista.add("danztvwq");
		Lista.add("elmoztvwq");
		Lista.add("francescaztvwq");
		Lista.add("gabrielztvwq");
		Lista.add("hazelztvwq");
		Lista.add("irisztvwq");
		Lista.add("jennyztvwq");
		Lista.add("katieztvwq");
		Lista.add("leeztvwq");
		Lista.add("mauriceztvwq");
		Lista.add("nicoleztvwq");
		Lista.add("oliverztvwq");
		Lista.add("otavioztvwq");
		Lista.add("pavelztvwq");
		Lista.add("quasimztvwq");
		Lista.add("rebeccaztvwq");
		Lista.add("samztvwq");
		Lista.add("timztvwq");
		Lista.add("urielztvwq");
		Lista.add("vabreuztvwq");
		Lista.add("xandraztvwq");
		Lista.add("yvesztvwq");
		Lista.add("zoeztvwq");
		Lista.add("alice_juniorztvwq");
		Lista.add("bob_juniorztvwq");
		Lista.add("claire_juniorztvwq");
		Lista.add("dan_juniorztvwq");
		Lista.add("elmo_juniorztvwq");
		Lista.add("francesca_juniorztvwq");
		Lista.add("gabriel_juniorztvwq");
		Lista.add("hazel_juniorztvwq");
		Lista.add("iris_juniorztvwq");
		Lista.add("jenny_juniorztvwq");
		Lista.add("katie_juniorztvwq");
		Lista.add("lee_juniorztvwq");
		Lista.add("maurice_juniorztvwq");
		Lista.add("nicole_juniorztvwq");
		Lista.add("oliver_juniorztvwq");
		Lista.add("otavio_juniorztvwq");
		Lista.add("pavel_juniorztvwq");
		Lista.add("quasim_juniorztvwq");
		Lista.add("rebecca_juniorztvwq");
		Lista.add("sam_juniorztvwq");
		Lista.add("tim_juniorztvwq");
		Lista.add("uriel_juniorztvwq");
		Lista.add("vabreu_juniorztvwq");
		Lista.add("vilmar_juniorztvwq");
		Lista.add("xandra_juniorztvwq");
		Lista.add("yves_juniorztvwq");
		Lista.add("zoe_juniorztvwq");
		Lista.add("rosadaztvwq");
		Lista.add("achocolatadaztvwq");
		Lista.add("greloztvwq");
		Lista.add("choc_turboztvwq");
		Lista.add("greicyztvwq");
		Lista.add("vesgaztvwq");
		Lista.add("rosada_jrztvwq");
		Lista.add("minhocaztvwq");
		Lista.add("faveladaztvwq");
		Lista.add("jenny_abreuztvwq");
		Lista.add("katie_abreuztvwq");
		Lista.add("lee_abreuztvwq");
		Lista.add("maurice_abreuztvwq");
		Lista.add("nicole_abreuztvwq");
		Lista.add("oliver_abreuztvwq");
		Lista.add("otavio_abreuztvwq");
		Lista.add("pavel_abreuztvwq");
		Lista.add("quasim_abreuztvwq");
		Lista.add("rebecca_abreuztvwq");
		Lista.add("sam_abreuztvwq");
		Lista.add("tim_abreuztvwq");
		Lista.add("uriel_abreuztvwq");
		Lista.add("vabreu_abreuztvwq");
		Lista.add("xandra_abreuztvwq");
		Lista.add("yves_abreuztvwq");
		Lista.add("zoe_abreuztvwq");
		Lista.add("alice_junior_abreuztvwq");
		Lista.add("bob_junior_abreuztvwq");
		Lista.add("claire_junior_abreuztvwq");
		Lista.add("dan_junior_abreuztvwq");
		Lista.add("elmo_junior_abreuztvwq");
		Lista.add("francesca_junior_abreuztvwq");
		Lista.add("gabriel_junior_abreuztvwq");
		Lista.add("hazel_junior_abreuztvwq");
		Lista.add("iris_junior_abreuztvwq");
		Lista.add("jenny_junior_abreuztvwq");
		Lista.add("katie_junior_abreuztvwq");
		Lista.add("lee_junior_abreuztvwq");
		Lista.add("maurice_junior_abreuztvwq");
		Lista.add("nicole_junior_abreuztvwq");
		Lista.add("oliver_junior_abreuztvwq");
		Lista.add("otavio_junior_abreuztvwq");
		Lista.add("pavel_junior_abreuztvwq");
		Lista.add("quasim_junior_abreuztvwq");
		Lista.add("rebecca_junior_abreuztvwq");
		Lista.add("sam_junior_abreuztvwq");
		Lista.add("tim_junior_abreuztvwq");
		Lista.add("uriel_junior_abreuztvwq");
		Lista.add("vabreu_junior_abreuztvwq");
		Lista.add("vilmar_junior_abreuztvwq");
		Lista.add("xandra_junior_abreuztvwq");
		Lista.add("yves_junior_abreuztvwq");
		Lista.add("zoe_junior_abreuztvwq");
		return Lista;
	}

}
