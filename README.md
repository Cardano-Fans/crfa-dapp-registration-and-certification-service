# crfa-dapp-registration-and-certification-service

## Introduction
Reference implementation (WIP) of CIP-72 (dApp registration) and dApp Certification CIP.

- https://github.com/cardano-foundation/CIPs/pull/355/files
- https://github.com/cardano-foundation/CIPs/pull/499/files

## native image

Create native image (GraalVM 22.3+ is required)
```
$ ./gradlew nativeCompile
```

then, you can run the app as follows:
```
$ build/native/nativeCompile/crfa-dapp-registration-and-certification-service
```

## Create docker image
```
$ ./gradlew bootBuildImage
```
and then run via
 
```
$ docker run --rm -p 8080:8080 crfa-dapp-registration-and-certification-service:0.0.1-SNAPSHOT
```

## Design

### dApp ownership, one subject = one team
Store owner will need to manually map pub key (signature) to dApp subject. e.g. subject xyz is represented by team abc (signature).
Without such a mapping, it is unclear whether the dApp claim is actually valid / not spoof. Optimistically
assuming that first subject + signature could be error prone / hack prone.

Without DIDs, the current recommended method to assert this trust is to use off-chain communication, e.g. Twitter / Discord.
Once entry is created store will properly recognise the app.

Signature validation is partially manual, partially automated process. Signature validation must be done based on blake2b
signatures but what we need to do manually is approval that a certain given public key on chain indeed belongs
to a particular team. We will use as mentioned manual verification tools such as Discord / Twitter.

Question, there could be a situation that one team has multiple signatures rather than one to one mapping?

### Process Description
a) We store dApp registration events from Cardano without any verification
b) We create on-chain dApp entry with or without versions
c) We crawl off-chain data (as a job) / asynchronously but we can also crawl upon trigger from on chain crawling process (but async)
d) We verify signatures and upon automatic and manual verification we create VerifiedDappSignature entry
e) Upon verification dApp / dApp version is ready to be shown, it should be hidden otherwise
f) there maybe problems crawling off-chain data for a dApp, we simply keep trying a few times until we give up with some exponential delay function
g) dApp de-registration means we will still have it in our DB but will be either marked deprecated or simply even removed from the view (controller methods)
h) we need to handle both dApp version deregistration as well as whole dApp deregistration
i) dApps do not have to have versions (!)
j) we need to convert dApp onchain json to canonical json and off-chain as well before we attest that blake2b signatures are matching
