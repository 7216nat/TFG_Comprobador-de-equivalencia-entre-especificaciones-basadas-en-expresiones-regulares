package objects;

import java.util.ArrayList;
import java.util.HashSet;

public class BerrySethiNode {
	
	protected String sim;
	protected boolean empty;
	protected Tipo tipo;
	protected HashSet<Integer> first;
	protected HashSet<Integer> last;
	protected HashSet<Integer> fol;
	
	protected BerrySethiNode left;
	protected BerrySethiNode right;
	
	public BerrySethiNode() {
		this(null);
	}
	
	public BerrySethiNode(BerrySethiNode left) {
		this.left = left;
		this.right = null;
		this.fol = new HashSet<>();
	}
	
	public BerrySethiNode(BerrySethiNode left, BerrySethiNode right) {
		this.left = left;
		this.right = right;
		this.fol = new HashSet<>();
	}
	
	public boolean getEmpty(){return empty;}
	public void setEmpty(boolean empty){this.empty = empty;}
	public HashSet<Integer> getFirst(){return first;}
	public void setFirst(HashSet<Integer> first){this.first = first;}
	public HashSet<Integer> getLast(){return last;}	
	public void setLast(HashSet<Integer> last){this.last = last;}
	public HashSet<Integer> getFollow(){return fol;}	
	public void setFollow(HashSet<Integer> follow){this.fol = follow;}
	public String getSim() { return sim; }
	public void setSim(String sim) { this.sim = sim; }
	public void setTipo(Tipo tipo) { this.tipo = tipo; }
	
	public boolean esHoja() {
		return left == null && right == null;
	}
	
	@Override
	public String toString() {
		String str = "["+ sim +"] " + first.toString() + last.toString() + fol.toString() + '\n';
		if (left != null)
			str += left.toString()+ '\n' ;
		if (right != null)
			str += right.toString(); 
		return str;
	}
	

	
	public void buildEstados(ArrayList<BerrySethiNode> list, HashSet<Integer> follow){
		switch(this.tipo) {
			case CONCAT:
				this.fol.addAll(follow);
				if (right.empty) {
					follow.addAll(right.first);
					left.buildEstados(list, follow);
				}
				else {
					follow.clear();
					follow.addAll(right.first);
					left.buildEstados(list, follow);
				}
				follow.clear();
				follow.addAll(fol);
				right.buildEstados(list, follow);
				break;
			case SIMB:
				if (this.esHoja()) {
					this.fol.addAll(follow);
					list.add(this);
				}
				break;
			case RANGO:
				if (this.esHoja()) {
					this.fol.addAll(follow);
					list.add(this);
				}
				break;
//			case UNIONRANGOS:
//				fol.addAll(follow);
//				left.buildEstados(list, follow);
//				break;
			case UNION:
				this.fol.addAll(follow);
				left.buildEstados(list, follow);
				follow.clear();
				follow.addAll(fol);
				right.buildEstados(list, follow);
				break;
			case KLEEN:
				this.fol.addAll(follow);
				follow.addAll(left.first);
				left.buildEstados(list, follow);
				break;
//			case KLEENPOS:
//				this.fol.addAll(follow);
//				follow.addAll(left.first);
//				left.buildEstados(list, follow);
//				break;
			case LAMBDA:
//				if (this.esHoja()) {
//					this.fol.addAll(follow);
//					//list.add(this);
//				}
				break;
//			case LAMBDAEXP:
//				this.fol.addAll(follow);
//				left.buildEstados(list, follow);
//				break;
			case VACIO:
//				if (this.esHoja()) {
//					this.fol.addAll(follow);
//					list.add(this);
//				}
				break;
			default:
				break;
		}
	}
}
