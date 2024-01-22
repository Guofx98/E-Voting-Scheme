// SPDX-License-Identifier: MIT
pragma solidity ^0.8.22;

import "./BLS.sol";


contract Registration{

    BLS.E1Point [] mList;
    string [] infoList;

    mapping (bytes => bool) public pkList;

    
    address constant CA= /* CA's Address */;
    address constant O = /* O's Address */;
    address constant C = /* C's Address */;


    BLS.E2Point bpk = BLS.E2Point(
            [15516709285352539082439213720585739724329002971882390582209636960597958801449, 19324541677661060388134143597417835654030498723817274130329567224531700170734],
            [16550775633156536193089672538964908973667410921848053632462693002610771214528, 10154483139478025296468271477739414260393126999813603835827647034319242387010]
    );
    
    struct signs{
        BLS.E1Point sig_CA;
        BLS.E1Point sig_O;
        BLS.E1Point sig_C;
        bool CA;
        bool O;
        bool C;
    }

    mapping (uint256 => signs) public signList;

    function requestSig(uint256 x,uint256 y, string memory idInfo) public returns (uint256){
        BLS.E1Point memory m = BLS.E1Point(x,y);
        mList.push(m);
        infoList.push(idInfo);
        return mList.length-1;
    }

    function getMessage(uint8 index) public view returns(BLS.E1Point memory m, string memory idInfo){
        return (mList[index], infoList[index]);
    }

    function uploadSig(uint256 index, uint256 x,uint256 y) public{
        BLS.E1Point memory sign = BLS.E1Point(x,y);
        if(msg.sender==CA){
            signList[index].sig_CA=sign;
            signList[index].CA==true;
        }
        else if(msg.sender==O){
            signList[index].sig_O=sign;
            signList[index].O==true;
        }
        else if(msg.sender==C){
            signList[index].sig_C=sign;
            signList[index].C==true;
        }
    }

    function getSig(uint256 index) public view returns (BLS.E1Point memory){
        if(signList[index].CA==true&&signList[index].C==true&&signList[index].O==true){
            BLS.E1Point memory tmp = BLS.addCurveE1(signList[index].sig_CA,signList[index].sig_O);
            return BLS.addCurveE1(tmp,signList[index].sig_C);
        }
        return BLS.E1Point(uint256(0),uint256(0));
    }

    function regist(string memory pki, uint256 x,uint256 y) public returns (bool){
        bytes memory pk = bytes(pki);
        BLS.E1Point memory sig = BLS.E1Point(x,y);
        bool check_sig = BLS.verify(bpk,pk,sig);
        if(check_sig&&!pkList[pk]){
            pkList[pk]=true;
            return true;
        }
        return false;
    }
}