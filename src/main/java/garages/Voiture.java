package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();

	public Voiture(String i) {
		if (null == i) {
			throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
		}

		immatriculation = i;
	}

	public String getImmatriculation() {

		return immatriculation;
	}

	public List<Stationnement> getMyStationnements(){
		return myStationnements;
	}

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws java.lang.Exception Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws Exception {
		// Et si la voiture est déjà dans un garage ?
			if (estDansUnGarage())
				throw new Exception("Est deja dans un garage");
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws java.lang.Exception si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws Exception {
		if (!this.estDansUnGarage()){
			throw new Exception ("La voiture n'est dans aucun garage donc elle ne peut pas sortir");
		}
		if (this.estDansUnGarage()){
			Stationnement dernier = myStationnements.get(myStationnements.size()-1);
			dernier.terminer();
		}
		// Trouver le dernier stationnement de la voiture
		// Terminer ce stationnement
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> set = new HashSet();
		// Ajout de la liste des garages visités
		for (Stationnement s : myStationnements){
			set.add(s.getGarage());
		}
		return set;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// on regarde si un stationnement est en cours
		if (myStationnements.size()-1 == -1){
			return false;
		}
		Stationnement dernier = myStationnements.get(myStationnements.size()-1); // on recupere le dernier stationnement
		if (dernier.estEnCours()){
			return true;
			// vrai si dernier stationnement en cours
		} else {
			return false;
		}
		// Vrai si le dernier stationnement est en cours
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * dates d'entrée / sortie dans ce garage
	 * <br>
	 * Exemple :
	 * 
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out)
	 */
	public void imprimeStationnements(PrintStream out) {
		List<Stationnement> stationnements = new LinkedList<>(myStationnements);
		// Liste des stationnements

		for (int i = 0; i < stationnements.size(); i++) {
			// On parcourt la liste
			String garage = stationnements.get(i).getGarage().toString();
			// Variable de type string pour les garages
			out.append(garage + "\n");
			out.append(stationnements.get(i).toString() + "\n");

			for (int j = i + 1; j < stationnements.size(); j++) {
				// Si on a deux fois le même garage il ne s'affiche qu'une seule fois
				if (stationnements.get(j).getGarage() == stationnements.get(i).getGarage()) {
					out.append(stationnements.get(j).toString() + "\n");
					stationnements.remove(stationnements.get(j));
					// On le supprime de notre liste
				}
			}

		}
	}
}
