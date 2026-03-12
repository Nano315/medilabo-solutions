export interface Patient {
  id: number;
  prenom: string;
  nom: string;
  dateNaissance: string;
  genre: string;
  adressePostale?: string;
  telephone?: string;
}
