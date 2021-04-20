package objects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BerrySethiNode {
	
	protected String sim;
	protected boolean empty;
	protected Tipo tipo;
	protected Set<Integer> first;
	protected Set<Integer> last;
	protected Set<Integer> fol;
	
	protected BerrySethiNode left;
	protected BerrySethiNode right;
	
	/**
	 * constructora por defecto
	 */
	public BerrySethiNode() {
		this(null);
	}
	
	/**
	 * construtora para un nodo unario
	 * @param left: hijo
	 */
	public BerrySethiNode(BerrySethiNode left) {
		this.left = left;
		this.right = null;
		this.fol = new HashSet<>();
	}
	
	/**
	 * constructora para un nodo binario
	 * @param left: hijo izq
	 * @param right: hijo dch
	 */
	public BerrySethiNode(BerrySethiNode left, BerrySethiNode right) {
		this.left = left;
		this.right = right;
		this.fol = new HashSet<>();
	}
	
	/**
	 * get empty
	 * @return: es empty?
	 */
	public boolean getEmpty(){return empty;}
	/**
	 * set empty
	 * @param empty = empty
	 */
	public void setEmpty(boolean empty){this.empty = empty;}
	/*
	 * get set de first
	 */
	public Set<Integer> getFirst(){return first;}
	/**
	 * set set de first
	 * @param first = first
	 */
	public void setFirst(Set<Integer> first){this.first = first;}
	/*
	 * get set de last
	 */
	public Set<Integer> getLast(){return last;}	
	/**
	 * set set last
	 * @param last = last
	 */
	public void setLast(Set<Integer> last){this.last = last;}
	/**
	 * get set de follow
	 * @return set de follow
	 */
	public Set<Integer> getFollow(){return fol;}
	/**
	 * set set de follow
	 * @param follow = follow
	 */
	public void setFollow(Set<Integer> follow){this.fol = follow;}
	/**
	 * get sim
	 * @return sim
	 */
	public String getSim() { return sim; }
	/**
	 * set sim
	 * @param sim = sim
	 */
	public void setSim(String sim) { this.sim = sim; }
	/**
	 * set tipo
	 * @param tipo = tipo
	 */
	public void setTipo(Tipo tipo) { this.tipo = tipo; }
	/**
	 * get tipo
	 * @return Tipo tipo
	 */
	public Tipo getTipo() { return this.tipo; }
	
	/**
	 * es hoja?
	 * @return nodo == hoja
	 */
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
	

	/**
	 * construye los estados propagando e rellenando el set de follow
	 * @param list: lista de estados a rellenar
	 * @param follow: set de follow a propagar
	 */
	public void buildEstados(List<BerrySethiNode> list, Set<Integer> follow){
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
