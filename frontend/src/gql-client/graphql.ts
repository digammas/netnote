/* eslint-disable */
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
};

export type Mutation = {
  __typename?: 'Mutation';
  rate?: Maybe<Scalars['Boolean']['output']>;
};


export type MutationRateArgs = {
  input: RatingInput;
};

export type NoteOutput = {
  __typename?: 'NoteOutput';
  average?: Maybe<Scalars['Float']['output']>;
  support: Scalars['Int']['output'];
};

export type PageInfo = {
  __typename?: 'PageInfo';
  endCursor?: Maybe<Scalars['String']['output']>;
  hasNextPage: Scalars['Boolean']['output'];
  hasPreviousPage: Scalars['Boolean']['output'];
  startCursor?: Maybe<Scalars['String']['output']>;
};

export type Query = {
  __typename?: 'Query';
  domainNote?: Maybe<NoteOutput>;
  domainRatings?: Maybe<RatingConnection>;
  linkNote?: Maybe<NoteOutput>;
  linkRatings?: Maybe<RatingConnection>;
};


export type QueryDomainNoteArgs = {
  url: Scalars['String']['input'];
};


export type QueryDomainRatingsArgs = {
  after?: InputMaybe<Scalars['String']['input']>;
  first?: InputMaybe<Scalars['Int']['input']>;
  url: Scalars['String']['input'];
};


export type QueryLinkNoteArgs = {
  url: Scalars['String']['input'];
};


export type QueryLinkRatingsArgs = {
  after?: InputMaybe<Scalars['String']['input']>;
  first?: InputMaybe<Scalars['Int']['input']>;
  url: Scalars['String']['input'];
};

export type RatingConnection = {
  __typename?: 'RatingConnection';
  edges: Array<Maybe<RatingEdge>>;
  pageInfo: PageInfo;
};

export type RatingEdge = {
  __typename?: 'RatingEdge';
  cursor?: Maybe<Scalars['String']['output']>;
  node: RatingOutput;
};

export type RatingInput = {
  comment?: InputMaybe<Scalars['String']['input']>;
  note: Scalars['Int']['input'];
  url: Scalars['String']['input'];
};

export type RatingOutput = {
  __typename?: 'RatingOutput';
  comment?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  note: Scalars['Int']['output'];
};
