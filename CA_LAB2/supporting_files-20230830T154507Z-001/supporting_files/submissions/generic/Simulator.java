package generic;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import generic.Operand.OperandType;
import generic.Instruction.OperationType;

public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	public static void assemble(String objectProgramFile)
	{
		// TODO your assembler code
		// 1. open the objectProgramFile in binary mode
		// 2. write the firstCodeAddress to the file
		// 3. write the data to the file
		// 4. assemble one instruction at a time, and write to the file
		// 5. close the file
		try{
            FileOutputStream fileOs=new FileOutputStream(objectProgramFile);
         	DataOutputStream os = new DataOutputStream(fileOs);
			 os.writeInt(ParsedProgram.code.get(0).getProgramCounter());
			
            for(int i=0;i<ParsedProgram.data.size();i++){
				os.writeInt(ParsedProgram.data.get(i));
			}
				// os.writeInt(2);
				// os.writeInt(10);
				// os.writeInt(20);
				// os.writeInt(-1341652992);
           		 // os.writeInt(-402653184);

			for(int i=0;i<ParsedProgram.code.size();i++){
				Instruction ins=ParsedProgram.code.get(i);
				OperationType o=ins.getOperationType();
				int num_operation=o.ordinal();
				if(0<=num_operation && num_operation<=21){
					if(num_operation%2==0){//R3-type
						int n1=ins.getSourceOperand1().value;
						int n2=ins.getSourceOperand2().value;
						int dest=ins.getDestinationOperand().value;
						num_operation=num_operation<<27;
						n1=n1<<22;
						n2=n2<<17;
						dest=dest<<12;
						int final_num=num_operation|n1|n2|dest;
						os.writeInt(final_num);

					}else{//R2I-type
						int n1=ins.getSourceOperand1().value;
						int dest=ins.getDestinationOperand().value;
						int imme=0;
						if(ins.getSourceOperand2().getOperandType()==Operand.OperandType.Immediate){
							 imme=ins.getSourceOperand2().value;
						}else if(ins.getSourceOperand2().getOperandType()==Operand.OperandType.Label){
							 imme=ParsedProgram.symtab.get(ins.getSourceOperand2().getLabelValue())-ins.getProgramCounter();
						}
						num_operation=num_operation<<27;
						n1=n1<<22;
						dest=dest<<17;
						int final_num=num_operation|n1|dest|imme;
						os.writeInt(final_num);

					}
				}else if(num_operation==24){//jump RI
					int dest=0;
					if(ins.getDestinationOperand().getOperandType()==Operand.OperandType.Immediate){
							 dest=ins.getDestinationOperand().value;
					}else if(ins.getDestinationOperand().getOperandType()==Operand.OperandType.Register){
							 dest=ins.getDestinationOperand().value;
					}else{
						String labe=ins.getDestinationOperand().getLabelValue();
						dest=ParsedProgram.symtab.get(labe)-ins.getProgramCounter();
					}
					num_operation=num_operation<<27;
					dest=dest<<17;
					int final_num=num_operation|dest;
					os.writeInt(final_num);
				}else if(num_operation==22 || num_operation==23){//load and store R2I
						int n1=ins.getSourceOperand1().value;
						int dest=ins.getDestinationOperand().value;
						int imme=0;
						if(ins.getSourceOperand2().getOperandType()==Operand.OperandType.Immediate){
							 imme=ins.getSourceOperand2().value;
						}else if(ins.getSourceOperand2().getOperandType()==Operand.OperandType.Label){
							 imme=ParsedProgram.symtab.get(ins.getSourceOperand2().getLabelValue());
						}
						num_operation=num_operation<<27;
						n1=n1<<22;
						dest=dest<<17;
						int final_num=num_operation|n1|dest|imme;
						os.writeInt(final_num);
				}
				else if (25<=num_operation && num_operation<=28){// beq,bne,blt,bgt R2I
						int n1=ins.getSourceOperand1().value;
						int n2=ins.getSourceOperand2().value;
						int imme=0;
						if(ins.getDestinationOperand().getOperandType()==Operand.OperandType.Immediate){
							 imme=ins.getDestinationOperand().value;
						}else if(ins.getDestinationOperand().getOperandType()==Operand.OperandType.Label){
								String labe=ins.getDestinationOperand().getLabelValue();
						 		imme=ParsedProgram.symtab.get(labe)-ins.getProgramCounter();
						}
					
						num_operation=num_operation<<27;
						n1=n1<<22;
						n2=n2<<17;
						int final_num=num_operation|n1|n2|imme;
						os.writeInt(final_num);
				}else if (num_operation==29){//end RI
					num_operation=num_operation<<27;
					os.writeInt(num_operation);
				}
			}
			
			os.close();
			fileOs.close();	
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
	}

	
}
