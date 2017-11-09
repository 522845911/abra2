package abra;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class SemiGlobalAlignerTest {
	
	private SemiGlobalAligner sg = new SemiGlobalAligner(8,-32,-48,-1);

	@Test (groups = "unit")
	public void testInsert() {
		String ref = "ATCGAATTCCGGGCTA";
		String seq = "GAACCCCTTCCG";
		
		SemiGlobalAligner.Result res = sg.align(seq, ref);
		assertEquals(res.position, 3);
		assertEquals(res.cigar, "3M4I5M");
	}
	
	@Test (groups = "unit")
	public void testDelete() {
		String ref = "ATCGAATTCCGGGCTA";
		String seq = "AATTCTA";
		SemiGlobalAligner.Result res = sg.align(seq, ref);
		assertEquals(res.position, 4);
		assertEquals(res.cigar, "4M5D3M");
//		assertEquals(res.cigar, "5M5D2M");
		assertEquals(res.score, 4);
		System.out.println("score: " + res.score);
	}

	@Test (groups = "unit")
	public void testExactMatch() {
		String ref = "ATCGAATTCCGGGCTA";
		String seq = "ATCGAATT";
		SemiGlobalAligner.Result res = sg.align(seq, ref);
		assertEquals(res.position, 0);
		assertEquals(res.cigar, "8M");
	}
	
	@Test (groups = "unit")
	public void testMismatches() {
		String ref = "ATCGAATTCCGGGCTA";
		String seq = "ATTGACTT";
		SemiGlobalAligner.Result res = sg.align(seq, ref);
		assertEquals(res.position, 0);
		assertEquals(res.cigar, "8M");
	}
	
	@Test (groups = "unit")
	public void testEndToEnd() {
		String ref = "ATCGAATTCCGGGCTA";
		String seq = "ATCGAATTCCGGGCTA";
		SemiGlobalAligner.Result res = sg.align(seq, ref);
		assertEquals(res.position, 0);
		assertEquals(res.cigar, "16M");
	}
	
	@Test (groups = "unit")
	public void testBigDel() {
		String seq = "GGGCTGCCGTTTTTCCATTACGGCTTTCGTAATGTGACCACGTGCTTTTGA";
		String ref = "GGGCTGCCGTTTTTCCATTACGGCTTTCCTTTGAAGTATATTTTAGGACATGACAGTCTTGTACCTGAAGTAATGTGACCACGTGCTTTTGA";
		
//		SemiGlobalAligner sg2 = new SemiGlobalAligner(1,-4,-8,0);
		SemiGlobalAligner.Result res = sg.align(seq, ref);
		assertEquals(res.position, 0);
		assertEquals(res.cigar, "28M41D23M");
//		assertEquals(res.cigar, "23M41D28M");
		assertEquals(res.score, 320);
//		assertEquals(res.score, 200);
	}
	
	@Test (groups = "unit")
	public void testUnderflowBug() {
		String seq = "ATCCAACGTGATTAAGGATAGGAATCGGTAGTTTGGTTTTTTTGTTTGTTTTGTTTTTTTAACCACAGATAATTGCCAAAGTTTCCACCTGAGGACGGTGTTTGGAGGTTGCCTTTTGGACCTACCACTTTGCTCATTCTTGCTAACCTAGTCTAGGTGACCTACAGTGCCGTGCATTTAAGTCAATGGTTGTTAAAAGAAGTTTCCCGTGTTGTAAATCATGTTTCCCTTATCAGATCATTTGCAAATACATTTAAATGATCTCATGGTAAATGTTGATGTATTTTTTGGTTTATTTTGTGTACTAACATAATAGAGAGAGACTCAGCTCCTTTTATTTATTTTGTTGATTTATGGATCAAATTCTAAAATAAAGTTGCCTGTTGTGACTTTTGTCCCATCTACTGCATACTTAGTGCTGAGATCCCTGTAAAATGTTTTGATGAAAATATGTATGTAGAGTCCAGTCGCATTATACATACATTTCATAAATAATTTTATGAACGTGCTGAACCTTCTTAAATGCCTACTCATTCAGCTTAAACAGGCTGAAGCCAAGTATGACAAAGAGGGGAAGGGCCAAAAACATAATCAAAGAATAATTTTAAAGAGAATTCTTGTCTCTCTTGCAAAAATAAAAAAATAAAAATACAAAAAATTTAACCTGCAATTTAGAAGCAGCAATGTTTAAAGCTTTTCTTGGCTTTGAAAGCAAAATTACCTTTGTGTCAGAT";
		String ref = "TTTGATGTACAGAATGGAAGAGTCTGTTGTTCTGTGGATTTTGAGTGTCTTCAAAATAATTGAAGTATTTACAGTGGACTCAACGCAGAAGAATGGACGAAATGACCATCCAACGTGATTAAGGATAGGAATCGGTAGTTTGGTTTTTTTGTTTGTTTTGTTTTTTTAACCACAGATAATTGCCAAAGTTTCCACCTGAGGACGGTGTTTGGAGGTTGCCTTTTGGACCTACCACTTTGCTCATTCTTGCTAACCTAGTCTAGGTGACCTACAGTGCCGTGCATTTAAGTCAATGGTTGTTAAAAGAAGTTTCCCGTGTTGTAAATCATGTTTCCCTTATCAGATCATTTGCAAATACATTTAAATGATCTCATGGTAAATGTTGATGTATTTTTTGGTTTATTTTGTGTACTAACATAATAGAGAGAGACTCAGCTCCTTTTATTTATTTTGTTGATTTATGGATCAAATTCTAAAATAAAGTTGCCTGTTGTGACTTTTGTCCCATCTACTGCATACTTAGTGCTGAGATCCCTGTAAAATGTTTTGATGAAAATATGTATGTAGAGTCCAGTCGCATTATACATACATTTCATAGTGCTGAACCTTCTTAAATGCCTACTCATTCAGCTTAAACAGGCTGAAGCCAAGTATGACAAAGAGGGGAAGGGCCAAAAACATAATCAAAGAATAATTTTAAAGAGAATTCTTGTCTCTCTTGCAAAAATAAAAAAATAAAAATACAAAAAATTTAACCTGCAATTTAGAAGCAGCAATGTTTAAAGCTTTTCTTGGCTTTGAAAGCAAAATTACCTTTGTGTCAGATTTATGTAGTACTTGATCCTTTATAAAGCATCCCAGTTAGTTACTAATAAAGTTATAATCATAGTACATGTCAGAAGTATTGTCCCTATATTACAGAAGTGTGAATTA";
//		String seq = "TATGTAGAGTCCAGTCGCATTATACATACATTTCATAAATAATTTTATGAACGTGCTGAACCTTCTTAAATGCCTACTCATTCAGCTTAAACAGG";
//		String ref = "TATGTATGTAGAGTCCAGTCGCATTATACATACATTTCATAGTGCTGAACCTTCTTAAATGCCTACTCATTCAGCTTAAACAGGCTGAA";
		
//		String seq = "TTTGGAGGTTGCCTTTTGGACCTACCACTTTGCTCATTCTTGCTAACCTAGTCTAGGTGACCTACAGTGCCGTGCATTTAAGTCAATGGTTGTTAAAAGAAGTTTCCCGTGTTGTAAATCATGTTTCCCTTATCAGATCATTTGCAAATACATTTAAATGATCTCATGGTAAATGTTGATGTATTTTTTGGTTTATTTTGTGTACTAACATAATAGAGAGAGACTCAGCTCCTTTTATTTATTTTGTTGATTTATGGATCAAATTCTAAAATAAAGTTGCCTGTTGTGACTTTTGTCCCATCTACTGCATACTTAGTGCTGAGATCCCTGTAAAATGTTTTGATGAAAATATGTATGTAGAGTCCAGTCGCATTATACATACATTTCATAAATAATTTTATGAACGTGCTGAACCTTCTTAAATGCCTACTCATTCAGCTTAAACAGGCTGAAGCCAAGTATGACAAAGAGGGGAAGGGCCAAAAACATAATCAAAGAATAATTTTAAAGAGAATTCTTGTCTCTCTTGCAAAA";
//		String ref = "TTTGATGTACAGAATGGAAGAGTCTGTTGTTCTGTGGATTTTGAGTGTCTTCAAAATAATTGAAGTATTTACAGTGGACTCAACGCAGAAGAATGGACGAAATGACCATCCAACGTGATTAAGGATAGGAATCGGTAGTTTGGTTTTTTTGTTTGTTTTGTTTTTTTAACCACAGATAATTGCCAAAGTTTCCACCTGAGGACGGTGTTTGGAGGTTGCCTTTTGGACCTACCACTTTGCTCATTCTTGCTAACCTAGTCTAGGTGACCTACAGTGCCGTGCATTTAAGTCAATGGTTGTTAAAAGAAGTTTCCCGTGTTGTAAATCATGTTTCCCTTATCAGATCATTTGCAAATACATTTAAATGATCTCATGGTAAATGTTGATGTATTTTTTGGTTTATTTTGTGTACTAACATAATAGAGAGAGACTCAGCTCCTTTTATTTATTTTGTTGATTTATGGATCAAATTCTAAAATAAAGTTGCCTGTTGTGACTTTTGTCCCATCTACTGCATACTTAGTGCTGAGATCCCTGTAAAATGTTTTGATGAAAATATGTATGTAGAGTCCAGTCGCATTATACATACATTTCATAGTGCTGAACCTTCTTAAATGCCTACTCATTCAGCTTAAACAGGCTGAAGCCAAGTATGACAAAGAGGGGAAGGGCCAAAAACATAATCAAAGAATAATTTTAAAGAGAATTCTTGTCTCTCTTGCAAAAATAAAAAAATAAAAATACAAAAAATTTAACCTGCAATTTAGAAGCAGCAATGTTTAAAGCTTTTCTTGGCTTTGAAAGCAAAATTACCTTTGTGTCAGATTTATGTAGTACTTGATCCTTTATAAAGCATCCCAGTTAGTTACTAATAAAGTTATAATCATAGTACATGTCAGAAGTATTGTCCCTATATTACAGAAGTGTGAATTA";

		SemiGlobalAligner.Result res = sg.align(seq, ref);
		assertEquals(res.cigar, "490M15I229M");
//		assertEquals(res.cigar, "487M15I232M");
//		assertEquals(res.cigar, "489M15I230M");
		assertEquals(res.position, 107);
	}
	
	@Test (groups = "unit")
	public void testInsert2() {
//		String ref = "TCCTAGGGCCTAGCTGCCATCAGCCTGATGTGCAGCAGCCTCTGCAGCGGTGCTGACACCAGTTTACAAGGCCCTGAGAGGG";
//		String seq = "TCCTAGGGCCTAGCTGCCATCAGCCTGATGTGCAGCAGCCTCTGCAGGCTTCAGTTAGATTCATTTCGGTGCTGACACCAGTTTACAAGGCCCTGAGAGGG";
		String ref = "CAGCCTGATGTGCAGCAGCCTCTGCAGCGGTGCTGACACCA";
		String seq = "CAGCCTGATGTGCAGCAGCCTCTGCAGGCTTCAGTTAGATTCATTTCGGTGCTGACACCA";
		
		SemiGlobalAligner.Result res = sg.align(seq, ref);
		System.out.println(res);
	}
}
