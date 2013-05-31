using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;


using System.Xml.Serialization;
using System.Collections;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;

/// <summary>
/// Summary description for SemService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]
public class SemService : System.Web.Services.WebService {

    public SemService () {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }

    /// <summary>
    /// Web metoda vo koja se pravi povik do baza i se vrakaat site
    /// predmeti za dadeniot semestar. Rezultatot se parsira od DataSet vo
    /// lista na objekti od klasata Predmet. Listata se pakuva vo klasata
    /// Predmeti i se vraka kako rezultat.
    /// </summary>
    /// <param name="semestar">Semestar</param>
    /// <returns>Predmeti od dadeniot semestar</returns>

    [WebMethod]
    [XmlInclude (typeof(Predmet))]
    public Predmeti getPredmeti(int semestar) {
        Predmeti predmeti=new Predmeti();
        
        
        /*     **********     Konekcija    ************        */

        SqlConnection conn = new SqlConnection();
        conn.ConnectionString = ConfigurationManager.ConnectionStrings["conString"].ToString();

        string sql = "select * from Predmeti where semestar=@semestar";

        SqlCommand comm = new SqlCommand(sql);
        comm.Parameters.Add("@semestar", SqlDbType.Int);
        comm.Parameters["@semestar"].Value = semestar;
        comm.Connection = conn;

        SqlDataAdapter adapter = new SqlDataAdapter(comm);

        DataSet ds = new DataSet();

        try
        {
            conn.Open();
            int fill=adapter.Fill(ds);

            
        }
        catch (Exception)
        {

        }
        finally
        {
            conn.Close();
        }
        
        /*     ********** Polnenje lista    ********      */

        if (ds != null)
        {
            predmeti = predmetiFromDataSet(ds);
        }
        
        return predmeti;
    }

    /// <summary>
    /// Web metoda za logiranje
    /// </summary>
    /// <param name="username">Korisnicko ime</param>
    /// <param name="password">Lozinka</param>
    /// <returns>True ako logiranjeto e uspesno vo sprotivno false</returns>

    [WebMethod]
    public bool login(string username, string password)
    {
        SqlConnection conn = new SqlConnection();
        conn.ConnectionString = ConfigurationManager.ConnectionStrings["conString"].ToString();

        string sql = "select password from Korisnici where username=@username";

        SqlCommand comm = new SqlCommand(sql);
        comm.Parameters.Add("@username", SqlDbType.NVarChar);
        comm.Parameters["@username"].Value = username;
        comm.Connection = conn;
     

        try
        {
            conn.Open();
            SqlDataReader reader = comm.ExecuteReader();

            if (reader.Read())
            {
                string pom = reader.GetString(0);
                if (pom == password)
                    return true;
            }
        }
        catch (Exception)
        {
        }
        finally
        {
            conn.Close();
        }
        return false;
    }

    /// <summary>
    /// Metoda koja zapisuva vo baza eden predmet za daden korisnik
    /// </summary>
    /// <param name="username">Korisnicko ime ili br indeks</param>
    /// <param name="imePredmet">Ime na predmetot koj se prijavuva</param>
    /// <returns>True ake transakcijata e uspesna vo sprotivno false</returns>

    [WebMethod]
    public bool prijaviIspiti(string username, string imePredmet)
    {
        SqlConnection conn = new SqlConnection();
        conn.ConnectionString = ConfigurationManager.ConnectionStrings["conString"].ToString();

        string sql = "insert into Prijaveni values(@username, @imePredmet) ";

        SqlCommand comm = new SqlCommand(sql);
        comm.Parameters.Add("@username", SqlDbType.NVarChar);
        comm.Parameters["@username"].Value = username;

        comm.Parameters.Add("@imePredmet", SqlDbType.NVarChar);
        comm.Parameters["@imePredmet"].Value = imePredmet;

        comm.Connection = conn;

        try
        {
            conn.Open();
            int succ = comm.ExecuteNonQuery();

            if (succ != 0)
                return true;
            else return false;
        }
        catch (Exception)
        {
            return false;
        }
        finally
        {
            conn.Close();
        }
    }

    /// <summary>
    /// Lokalna metoda za parsiranje na objekt od tip DataSet
    /// Vraka objekt od klasata Predmeti
    /// </summary>
    /// <param name="ds">DataSet</param>
    /// <returns>Predmeti</returns>

    private Predmeti predmetiFromDataSet(DataSet ds)
    {
        Predmeti pred = new Predmeti();
        if (ds.Tables.Count > 0)
        {
            foreach (DataRow row in ds.Tables[0].Rows)
            {
                pred.Pred.Add(predmetFromDataRow(row));
            }
        }
        return pred;
    }

    /// <summary>
    /// Lokalna metoda koja vraka objekt od tip Predmet za daden DataRow objekt.
    /// Se koristi kako pomosna metoda vo metodata predmetiFromDataSet
    /// </summary>
    /// <param name="row">DataRow</param>
    /// <returns>Predmet</returns>

    private Predmet predmetFromDataRow(DataRow row)
    {
        Predmet item = new Predmet();

        if (!row.IsNull("ime"))
        {
            item.Ime = row["ime"].ToString();
        }
        if (!row.IsNull("sifra"))
        {
            item.Sifra = row["sifra"].ToString();
        }
        if (!row.IsNull("krediti"))
        {
            item.Krediti = Convert.ToDouble(row["krediti"]);
        }
        if (!row.IsNull("semestar"))
        {
            item.Semestar = Convert.ToInt32(row["semestar"]);
        }
        return item;
    }

    /// <summary>
    /// Klasa predmet so atributi ime (ime na predmet), sifra (sifra na predmet), krediti (broj na krediti)
    /// i semestar i soodvetni get i set metodi
    /// </summary>

    public class Predmet
    {
        private string ime;
        private string sifra;
        private double krediti;
        private int semestar;

        public string Ime
        {
            get { return ime; }
            set { ime = value; }
        }
        public string Sifra
        {
            get{ return sifra;}
            set{ sifra=value;}
        }
        public double Krediti
        {
            get { return krediti; }
            set { krediti = value; }
        }
        public int Semestar
        {
            get { return semestar; }
            set { semestar = value; }
        }

    }

    /// <summary>
    /// Klasa Predmeti koja sodrzi niza od obekti od tip Predmet
    /// Se koristi za vrakanje na rezultat vo Web metodata getPredmeti
    /// </summary>

    public class Predmeti
    {
        ArrayList predmeti = new ArrayList();

        public ArrayList Pred
        {
            get { return predmeti; }
            set { predmeti = value; }
        }
    }


}
