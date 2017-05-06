package edu.cmu.web3j;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.0.1.
 */
public final class SensorGroup extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b6105a68061001c6000396000f300606060405263ffffffff60e060020a6000350416636063ad1b8114610037578063bff90821146100de578063c2c1a1ed146101b4575bfe5b341561003f57fe5b6100ca600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284375050604080516020601f89358b0180359182018390048302840183019094528083529799988101979196509182019450925082915084018382808284375094965061021e95505050505050565b604080519115158252519081900360200190f35b34156100e657fe5b610134600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437509496506102f595505050505050565b60408051602080825283518183015283519192839290830191850190808383821561017a575b80518252602083111561017a57601f19909201916020918201910161015a565b505050905090810190601f1680156101a65780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156101bc57fe5b6100ca600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437509496506103fa95505050505050565b604080519115158252519081900360200190f35b6000610228610488565b838152602080820184905260016040808401919091525185518392600092889290918291908401908083835b602083106102735780518252601f199092019160209182019101610254565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208451805191946102b4945085935001906104b6565b5060208281015180516102cd92600185019201906104b6565b50604091909101516002909101805460ff1916911515919091179055600191505b5092915050565b6102fd610535565b60006000836040518082805190602001908083835b602083106103315780518252601f199092019160209182019101610312565b518151600019602094850361010090810a8201928316921993909316919091179092529490920196875260408051978890038201882060018181018054601f600293821615909902909601909516049586018390048302890183019091528488529750909450919250508301828280156103ec5780601f106103c1576101008083540402835291602001916103ec565b820191906000526020600020905b8154815290600101906020018083116103cf57829003601f168201915b505050505091505b50919050565b600060006000836040518082805190602001908083835b602083106104305780518252601f199092019160209182019101610411565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220600281015490935060ff16159150610478905057600191506103f4565b600091506103f4565b5b50919050565b60606040519081016040528061049c610535565b81526020016104a9610535565b8152600060209091015290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104f757805160ff1916838001178555610524565b82800160010185558215610524579182015b82811115610524578251825591602001919060010190610509565b5b50610531929150610559565b5090565b60408051602081019091526000815290565b60408051602081019091526000815290565b61057791905b80821115610531576000815560010161055f565b5090565b905600a165627a7a72305820f7905401a6942f71ba0ba0abc1dfaf50ae22c6753300d13a3382fb14555a5a620029";

    private SensorGroup(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private SensorGroup(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> addSensor(Utf8String sensorHash, Utf8String publicKey) {
        Function function = new Function("addSensor", Arrays.<Type>asList(sensorHash, publicKey), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Utf8String> getPublicKey(Utf8String sensorHash) {
        Function function = new Function("getPublicKey", 
                Arrays.<Type>asList(sensorHash), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> validateHash(Utf8String sensorHash) {
        Function function = new Function("validateHash", 
                Arrays.<Type>asList(sensorHash), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<SensorGroup> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(SensorGroup.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static Future<SensorGroup> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(SensorGroup.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static SensorGroup load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SensorGroup(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SensorGroup load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SensorGroup(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
