package battleships2;

import java.util.Random;
import java.util.Scanner;

public class Battleships_project2 {
			
	static final int SIZE = 7; // σταθερες για διαστασεις πινακα
	static int[][] board = new int[SIZE][SIZE]; // πινακας παιχνιδιου
	static int[][] ships = new int[4][2]; // θεσεις πλοιων
	static int attempts = 0; // αριθμος προσπαθειων
	static int shotHit = 0; // αριθμος χτυπημενων πλοιων
	static Scanner scanner = new Scanner(System.in); // σκανερ για αναγνωση βολων χρηστη
	
	public static void main(String[] args) {
		
		initBoard(); // αρχικοποιηση πινακα
        initShips(); // αρχικοποιηση πλοιων

		do { 
			showBoard(); // εμφανιση πινακα
			shoot(); // εκτελεση βολης
		} while (shotHit < 4); // δεν τελειωνει το παιχνιδι αν δεν βυθιστουν ολα τα πλοια
		
		System.out.println("Συγχαρητήρια! Βυθίσατε όλα τα πλοία σε " + attempts + " προσπάθειες."); // μηνυμα νικης
	}

	public static void initBoard() {
		// αρχικοποιηση πινακα με το συμβολο ~ (νερο)
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = -1; // ολες οι θεσεις ως νερο
			}
		}
	}

	public static void initShips() {
		Random rand = new Random();
		
		// τοποθετηση 4 πλοιων τυχαια στο πινακα
		for (int i = 0; i < 4; i++) {
			int row, col; 
			do {
				row = rand.nextInt(SIZE);
				col = rand.nextInt(SIZE);
			} while (isOccupied(row, col)); // ελεγχος θεσης (αν ειναι ελευθερη η οχι)
			
			ships[i][0] = row; // αποθηκευση γραμμης πλοιου
			ships[i][1] = col; // Ααποθηκευση στηλης πλοιου
			
		}
	}

	public static boolean isOccupied(int row, int col) {
		// ελεγχος θεσης (αν ειναι κατελλημενη απο πλοιο
		for (int i = 0; i < 4; i++) {
			if (ships[i][0] == row && ships[i][1] == col) {
				return true; 
			}
		}
		return false;
	}

	public static void showBoard() {
		// εμφανιση πινακα χωρις πλοια
		System.out.println("Πίνακας Παιχνιδιού:");
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] == -1) {
					System.out.print("~ "); // νερο
				} else if (board[i][j] == 0) {
					System.out.print("* "); // αστοχη βολη
				} else if (board[i][j] == 1) {
					System.out.print("X "); // χτυπημα
				}
			}
			System.out.println();
		}
	}

	public static void shoot() {
		int row, col; // ο χρηστης βαζει τις συντεταγμενες της βολης
		System.out.println("Δώστε την βολή σας.");
		System.out.print("Σειρά 1-7: ");
		row = scanner.nextInt() - 1;
		System.out.print("Στήλη 1-7: ");
		col = scanner.nextInt() - 1;
		
		// ελεγχος βολης (εγκυρη ή μη)
		if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
			System.out.println("Μη έγκυρη βολή. Προσπαθήστε ξανά.");
			return;
		}

		// ελεγχος βολης (πετυχημενη η αστοχη)
		if (hit(row, col)) {
			board[row][col] = 1; // χτυπημα
			shotHit++;
			System.out.println("Χτύπησες ένα πλοίο!");
		} else {
			board[row][col] = 0; // αστοχη βολη
			hint(row, col); // υποδειξεις
		}
		attempts++; // αυξανουμε τον αριθμο προσπαθειων
	}

	public static boolean hit(int row, int col) {
		// ελεγχουμε αν η βολη χτυπησε πλοιο
		for (int i = 0; i < 4; i++) {
			if (ships[i][0] == row && ships[i][1] == col) {
				return true;
			}
		}
		return false;
	}

	public static void hint(int row, int col) {
		// υποδειξεις για τον χρηστη
		int shipsInRow = 0;
		int shipsInCol = 0;
		
		for (int i = 0; i < 4; i++) {
			if (ships[i][0] == row) shipsInRow++;
		}
		for (int i = 0; i < 4; i++) {
			if (ships[i][1] == col) shipsInCol++;
		}
		System.out.println("Υποδείξεις: Στη σειρά υπάρχουν " + shipsInRow + " πλοία και στη στήλη υπάρχουν " + shipsInCol + " πλοία.");
	}
}