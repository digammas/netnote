import { ChangeEvent, FormEvent } from "react";
import { gql, useMutation } from '@apollo/client';

interface RateInput {
    url: string;
    note: number;
    comment: string | undefined;
}


const RATE = gql`
    mutation Rate($input: RatingInput!) {
        rate(input: $input)
    }
`;


export default function () {
    const [rate] = useMutation(RATE);
    const input: RateInput = {
        url: "",
        note: 0,
        comment: undefined
    };

    function handleUrlChange(e: ChangeEvent<HTMLInputElement>) {
        input.url = e.target.value;
    }

    function handleNoteChange(e: ChangeEvent<HTMLInputElement>) {
        input.note = parseInt(e.target.value);
    }

    function handleCommentChange(e: ChangeEvent<HTMLInputElement>) {
        input.comment = e.target.value || undefined;
    }

    async function handleSubmit(e: FormEvent) {
        e.preventDefault();
        await rate({ variables: { input } });
    }

    return <form onSubmit={handleSubmit}>
        <div>
            <label>URL</label><input id="link" onChange={handleUrlChange}></input>
        </div>
        <div>
            <label>Note</label><input type="range" min="0" max="5" step="1" id="note" value="0" onChange={handleNoteChange}/>
        </div>
        <div>
            <label>Comment</label><input id="comment" onChange={handleCommentChange}/>
        </div>
        <div>
            <input type="submit"/>
        </div>
    </form>
}
