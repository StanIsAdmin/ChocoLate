/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package independence;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;


public class Independence {
    
    private Model _model;
    private int _n;
    private IntVar[][] _t;
    private IntVar[][] _f;
    private IntVar[][] _c;
    
    public Independence(int n, int k1, int k2, int k3) {
        _n = n;
        _t = new IntVar[k1][2];
        _f = new IntVar[k2][2];
        _c = new IntVar[k3][2];
    }
    
    public void modelAndSolver(){
        initModel();
        setDomain();
        setConditions();
    }
    
    private void initModel() {
        _model = new Model("Independence");
    }
    
    private void setDomain() {
        for (int i = 1; i <= _t.length; i++){
            for (int j = 1; j <= 2; j++){
                _t[i][j] = _model.intVar("T"+i, 1, _n);
            }
        }
        for (int i = 1; i <= _f.length; i++){
            for (int j = 1; j <= 2; j++){
                _f[i][j] = _model.intVar("F"+i, 1, _n);
            }
        }
        for (int i = 1; i <= _c.length; i++){
            for (int j = 1; j <= 2; j++){
                _c[i][j] = _model.intVar("C"+i, 1, _n);
            }
        }
    }
    
    private void setConditions() {
        // Conditions tours
        // Pour chaques fous
        for (IntVar[] _t1 : _t) {
            for (IntVar[] _f1 : _f) {
                _model.arithm(_t1[0], "!=", _f1[0]);
                _model.arithm(_t1[1], "!=", _f1[1]);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
